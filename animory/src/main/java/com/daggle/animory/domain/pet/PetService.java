package com.daggle.animory.domain.pet;


import com.daggle.animory.domain.pet.dto.response.NewPetProfilesDto;
import com.daggle.animory.domain.pet.dto.response.PetProfilesDto;
import com.daggle.animory.domain.pet.dto.response.SosPetProfilesDto;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


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
}
