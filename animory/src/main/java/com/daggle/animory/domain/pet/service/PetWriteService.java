package com.daggle.animory.domain.pet.service;

import com.daggle.animory.common.error.exception.NotFound404;
import com.daggle.animory.domain.account.entity.Account;
import com.daggle.animory.domain.fileserver.S3FileRepository;
import com.daggle.animory.domain.pet.dto.request.PetRegisterRequestDto;
import com.daggle.animory.domain.pet.dto.request.PetUpdateRequestDto;
import com.daggle.animory.domain.pet.dto.response.RegisterPetSuccessDto;
import com.daggle.animory.domain.pet.dto.response.UpdatePetSuccessDto;
import com.daggle.animory.domain.pet.entity.Pet;
import com.daggle.animory.domain.pet.entity.PetPolygonProfile;
import com.daggle.animory.domain.pet.repository.PetPolygonRepository;
import com.daggle.animory.domain.pet.repository.PetRepository;
import com.daggle.animory.domain.shelter.ShelterRepository;
import com.daggle.animory.domain.shelter.entity.Shelter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;

@Service
@Transactional
@RequiredArgsConstructor
public class PetWriteService {

    private final S3FileRepository fileRepository;
    private final ShelterRepository shelterRepository;
    private final PetRepository petRepository;
    private final PetPolygonRepository petPolygonRepository;

    private final PetValidator petValidator;

    public RegisterPetSuccessDto registerPet(final Account account,
                                             final PetRegisterRequestDto petRequestDTO,
                                             final MultipartFile image,
                                             final MultipartFile video) {

        // 이미지, 숏폼 파일 저장 후 url 얻어오기
        // TODO: 이미지, 비디오 저장 요청 둘 중 하나가 실패했을 때, 또는 파일 저장에는 성공하였으나, 이후 DB Insert 등에 실패하였을 때, 파일 모두 삭제하는 Rollback 처리
        final URL imageUrl = fileRepository.save(image);
        final URL videoUrl = fileRepository.save(video);

        // 펫 등록을 요청한 유저의 보호소 조회
        final Shelter shelter = shelterRepository.findByAccountId(account.getId())
            .orElseThrow(() -> new NotFound404("반려동물을 등록하고자 하는 보호소 정보가 존재하지 않습니다."));

        // 펫 DB 저장
        final Pet registerPet = petRepository.save(
            petRequestDTO.toEntity(shelter, imageUrl.toString(), videoUrl.toString()));

        // 펫 다각형 프로필 DB 저장
        petPolygonRepository.save(petRequestDTO.petPolygonProfileDto().toEntity(registerPet));

        return new RegisterPetSuccessDto(registerPet.getId());
    }

    public UpdatePetSuccessDto updatePet(final Account account,
                                         final int petId,
                                         final PetUpdateRequestDto petUpdateRequestDto,
                                         final MultipartFile image,
                                         final MultipartFile video) {

        // 펫 id로 Pet, PetPolygonProfile 얻어오기
        final Pet updatePet = petRepository.findById(petId)
            .orElseThrow(() -> new NotFound404("등록되지 않은 펫입니다."));

        petValidator.validatePetUpdateAuthority(account, updatePet);

        final PetPolygonProfile petPolygonProfile = petPolygonRepository.findByPetId(petId)
            .orElseThrow(() -> new NotFound404("등록된 다각형 프로필이 존재하지 않습니다."));

        // 이미지, 비디오 파일 업데이트
        // TODO: 파일 수정 Rollback 처리
        updateFile(updatePet, image, video);

        // 펫 정보 업데이트
        updatePet.updateInfo(petUpdateRequestDto);
        petPolygonProfile.update(petUpdateRequestDto.petPolygonProfileDto());

        return new UpdatePetSuccessDto(updatePet.getId());
    }

    // 이미지, 비디오 파일 수정 요청 시 기존 파일 삭제 후 업데이트
    private void updateFile(final Pet updatePet, final MultipartFile image,
                           final MultipartFile video) {
        if (image != null) {
            final URL imageUrl = fileRepository.save(image);
            updatePet.updateImage(imageUrl.toString());
        }
        if (video != null) {
            final URL videoUrl = fileRepository.save(video);
            updatePet.updateVideo(videoUrl.toString());
        }
    }



}
