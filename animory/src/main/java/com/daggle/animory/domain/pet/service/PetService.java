package com.daggle.animory.domain.pet.service;


import com.daggle.animory.common.error.exception.NotFound404;
import com.daggle.animory.common.error.exception.UnAuthorized401;
import com.daggle.animory.domain.account.entity.Account;
import com.daggle.animory.domain.fileserver.FileRepository;
import com.daggle.animory.domain.fileserver.LocalFileRepository;
import com.daggle.animory.domain.pet.dto.request.PetRegisterRequestDto;
import com.daggle.animory.domain.pet.dto.request.PetUpdateRequestDto;
import com.daggle.animory.domain.pet.dto.response.*;
import com.daggle.animory.domain.pet.entity.Pet;
import com.daggle.animory.domain.pet.entity.PetPolygonProfile;
import com.daggle.animory.domain.pet.repository.PetPolygonRepository;
import com.daggle.animory.domain.pet.repository.PetRepository;
import com.daggle.animory.domain.shelter.ShelterRepository;
import com.daggle.animory.domain.shelter.entity.Shelter;
import java.net.URL;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
@RequiredArgsConstructor
public class PetService {

    private final LocalFileRepository fileRepository;
    private final ShelterRepository shelterRepository;
    private final PetRepository petRepository;
    private final PetPolygonRepository petPolygonRepository;

    public PetProfilesDto getPetProfiles() {
        throw new NotImplementedException("NotImplemented yet");
    }

    public SosPetProfilesDto getPetSosProfiles(final Pageable pageable) {
        throw new NotImplementedException("NotImplemented yet");
    }

    public NewPetProfilesDto getPetNewProfiles(final Pageable pageable) {
        throw new NotImplementedException("NotImplemented yet");
    }

    public PetDto getPetDetail(final int petId) {
        throw new NotImplementedException("NotImplemented yet");
    }

    public RegisterPetSuccessDto registerPet(final Account account,
            final PetRegisterRequestDto petRequestDTO, final MultipartFile image,
            final MultipartFile video) {

        // 이미지, 숏폼 파일 저장 후 url 얻어오기
        URL imageUrl = fileRepository.save(image);
        URL videoUrl = fileRepository.save(video);

        // 펫 등록을 요청한 유저의 보호소 조회
        Shelter shelter = shelterRepository.findByAccountId(account.getId())
                .orElseThrow(() -> new UnAuthorized401("보호소 정보가 존재하지 않습니다."));

        // 펫 DB 저장
        Pet registerPet = petRepository.save(
                petRequestDTO.toEntity(shelter, imageUrl.toString(), videoUrl.toString()));

        // 펫 다각형 프로필 DB 저장
        petPolygonRepository.save(petRequestDTO.petPolygonProfileDto().toEntity(registerPet));

        return new RegisterPetSuccessDto(registerPet.getId());
    }

    public UpdatePetSuccessDto updatePet(final int petId,
            final PetUpdateRequestDto petUpdateRequestDto, final MultipartFile image,
            final MultipartFile video) {

        // 펫 id로 Pet, PetPolygonProfile 얻어오기
        Pet updatePet = petRepository.findById(petId)
                .orElseThrow(() -> new NotFound404("등록되지 않은 펫입니다."));

        PetPolygonProfile petPolygonProfile = petPolygonRepository.findByPetId(petId)
                .orElseThrow(() -> new NotFound404("등록된 다각형 프로필이 존재하지 않습니다."));

        // 이미지, 비디오 파일 업데이트
        updateFile(updatePet, image, video);

        // 펫 정보 업데이트
        updatePet.updateInfo(petUpdateRequestDto);
        petPolygonProfile.update(petUpdateRequestDto.petPolygonProfileDto());

        return new UpdatePetSuccessDto(updatePet.getId());
    }

    // 이미지, 비디오 파일 수정 요청 시 기존 파일 삭제 후 업데이트
    public void updateFile(final Pet updatePet, final MultipartFile image,
            final MultipartFile video) {
        if (image != null) {
//            fileRepository.delete(updatePet.getProfileImageUrl());
            URL imageUrl = fileRepository.save(image);
            updatePet.updateImage(imageUrl.toString());
        }
        if (video != null) {
//            fileRepository.delete(updatePet.getProfileShortFormUrl());
            URL videoUrl = fileRepository.save(video);
            updatePet.updateVideo(videoUrl.toString());
        }
    }

    // 기존의 펫 등록 정보 조회
    public PetRegisterInfoDto getRegisterInfo(final int petId) {

        // 펫 id로 Pet, PetPolygonProfile 얻어오기
        Pet registerPet = petRepository.findById(petId)
                .orElseThrow(() -> new NotFound404("등록되지 않은 펫입니다."));

        PetPolygonProfile petPolygonProfile = petPolygonRepository.findByPetId(petId)
                .orElseThrow(() -> new NotFound404("등록된 다각형 프로필이 존재하지 않습니다."));

        // Pet -> PetRegisterInfoDto
        return PetRegisterInfoDto.fromEntity(registerPet, petPolygonProfile);
    }
}
