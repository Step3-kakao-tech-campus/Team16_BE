package com.daggle.animory.domain.pet;


import com.daggle.animory.domain.pet.dto.request.PetRegisterRequestDto;
import com.daggle.animory.domain.pet.dto.request.PetUpdateRequestDto;
import com.daggle.animory.domain.pet.dto.response.*;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;


@Service
public class PetService {

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

    public RegisterPetSuccessDto registerPet(final PetRegisterRequestDto petRequestDTO, final MultipartFile image, final MultipartFile video) {
        throw new NotImplementedException("NotImplemented yet");
    }

    public UpdatePetSuccessDto updatePet(final Optional<PetUpdateRequestDto> petUpdateRequestDto, final Optional<MultipartFile> image, final Optional<MultipartFile> video) {
        throw new NotImplementedException("NotImplemented yet");
    }
}
