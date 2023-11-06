package com.daggle.animory.domain.pet.service;

import com.daggle.animory.common.security.UserDetailsImpl;
import com.daggle.animory.domain.pet.dto.request.PetRegisterRequestDto;
import com.daggle.animory.domain.pet.dto.request.PetUpdateRequestDto;
import com.daggle.animory.domain.pet.dto.response.RegisterPetSuccessDto;
import com.daggle.animory.domain.pet.dto.response.UpdatePetSuccessDto;
import com.daggle.animory.domain.pet.entity.Pet;
import com.daggle.animory.domain.pet.exception.PetNotFoundException;
import com.daggle.animory.domain.pet.repository.PetRepository;
import com.daggle.animory.domain.shelter.ShelterRepository;
import com.daggle.animory.domain.shelter.entity.Shelter;
import com.daggle.animory.domain.shelter.exception.ShelterNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class PetWriteService {

    private final ShelterRepository shelterRepository;
    private final PetRepository petRepository;

    private final PetWriteServiceTransactionManager txManager;

    private final PetValidator petValidator;

    public RegisterPetSuccessDto registerPet(final UserDetailsImpl userDetails,
                                             final PetRegisterRequestDto petRequestDTO,
                                             final MultipartFile image,
                                             final MultipartFile video) {
        petValidator.validateImageFile(image);
        petValidator.validateVideoFile(video);

        // 펫 등록을 요청한 유저의 보호소 조회
        final Shelter shelter = shelterRepository.findByAccountEmail(userDetails.getEmail())
            .orElseThrow(() -> new ShelterNotFoundException());

        final Pet registerPet = txManager.doPetRegisterTransaction(petRequestDTO, image, video, shelter);

        return new RegisterPetSuccessDto(registerPet.getId());
    }

    public UpdatePetSuccessDto updatePet(final UserDetailsImpl userDetails,
                                         final int petId,
                                         final PetUpdateRequestDto petUpdateRequestDto,
                                         final MultipartFile image,
                                         final MultipartFile video) {
        petValidator.validateImageFile(image);
        petValidator.validateVideoFile(video);

        // 펫 id로 Pet 얻어오기
        final Pet updatePet = petRepository.findById(petId)
            .orElseThrow(() -> new PetNotFoundException());

        petValidator.validatePetUpdateAuthority(userDetails.getEmail(), updatePet);

        txManager.doPetUpdateTransaction(updatePet, petUpdateRequestDto, image, video);

        return new UpdatePetSuccessDto(updatePet.getId());
    }

    public void updatePetAdopted(final UserDetailsImpl userDetails,
                                 final int petId) {
        final Pet pet = petRepository.findById(petId)
            .orElseThrow(() -> new PetNotFoundException());

        petValidator.validatePetUpdateAuthority(userDetails.getEmail(), pet);

        // 입양상태를 YES로 변경하고, 보호 만료일을 null로 바꾼다.
        pet.setAdopted(); // TODO: 더 이상 보호소와 관련이 없어서.. 연결된 보호소 정보를 제거할 필요 ?
    }


}
