package com.daggle.animory.domain.pet.service;

import com.daggle.animory.common.error.exception.NotFound404Exception;
import com.daggle.animory.domain.account.entity.Account;
import com.daggle.animory.domain.fileserver.S3FileRepository;
import com.daggle.animory.domain.pet.dto.request.PetRegisterRequestDto;
import com.daggle.animory.domain.pet.dto.request.PetUpdateRequestDto;
import com.daggle.animory.domain.pet.dto.response.RegisterPetSuccessDto;
import com.daggle.animory.domain.pet.dto.response.UpdatePetSuccessDto;
import com.daggle.animory.domain.pet.entity.Pet;
import com.daggle.animory.domain.pet.repository.PetRepository;
import com.daggle.animory.domain.shelter.ShelterRepository;
import com.daggle.animory.domain.shelter.entity.Shelter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class PetWriteService {

    private final S3FileRepository fileRepository;
    private final ShelterRepository shelterRepository;
    private final PetRepository petRepository;

    private final PetWriteServiceTransactionManager txManager;

    private final PetValidator petValidator;

    public RegisterPetSuccessDto registerPet(final Account account,
                                             final PetRegisterRequestDto petRequestDTO,
                                             final MultipartFile image,
                                             final MultipartFile video) {
        petValidator.validateImageFile(image);
        petValidator.validateVideoFile(video);

        // 펫 등록을 요청한 유저의 보호소 조회
        final Shelter shelter = shelterRepository.findByAccountId(account.getId())
            .orElseThrow(() -> new NotFound404Exception("반려동물을 등록하고자 하는 보호소 정보가 존재하지 않습니다."));

        final Pet registerPet = txManager.doPetRegisterTransaction(petRequestDTO, image, video, shelter);

        return new RegisterPetSuccessDto(registerPet.getId());
    }



    public UpdatePetSuccessDto updatePet(final Account account,
                                         final int petId,
                                         final PetUpdateRequestDto petUpdateRequestDto,
                                         final MultipartFile image,
                                         final MultipartFile video) {
        petValidator.validateImageFile(image);
        petValidator.validateVideoFile(video);

        // 펫 id로 Pet, PetPolygonProfile 얻어오기
        final Pet updatePet = petRepository.findById(petId)
            .orElseThrow(() -> new NotFound404Exception("등록되지 않은 펫입니다."));

        petValidator.validatePetUpdateAuthority(account, updatePet);

        // 이미지, 비디오 파일 업데이트
        // TODO: 파일 수정 Rollback 처리
        updateFile(updatePet, image, video);

        // 펫 정보 업데이트
        updatePet.updateInfo(petUpdateRequestDto);

        return new UpdatePetSuccessDto(updatePet.getId());
    }

    public void updatePetAdopted(final Account account,
                                 final int petId) {
        final Pet pet = petRepository.findById(petId)
            .orElseThrow(() -> new NotFound404Exception("등록되지 않은 펫입니다."));

        petValidator.validatePetUpdateAuthority(account, pet);

        // 입양상태를 YES로 변경하고, 보호 만료일을 null로 바꾼다.
        pet.setAdopted(); // TODO: 더 이상 보호소와 관련이 없어서.. 연결된 보호소 정보를 제거할 필요 ?
    }

    // 이미지, 비디오 파일 수정 요청 시 기존 파일 삭제 후 업데이트
    private void updateFile(final Pet updatePet, final MultipartFile image,
                           final MultipartFile video) {
        final String imageUrl = fileRepository.save(image);
        updatePet.updateImage(imageUrl.toString());

        final String videoUrl = fileRepository.save(video);
        updatePet.updateVideo(videoUrl.toString());
    }


}
