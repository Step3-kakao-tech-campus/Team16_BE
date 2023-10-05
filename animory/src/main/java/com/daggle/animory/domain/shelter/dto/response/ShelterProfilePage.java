package com.daggle.animory.domain.shelter.dto.response;

import com.daggle.animory.domain.pet.entity.Pet;
import com.daggle.animory.domain.shelter.entity.Shelter;
import org.springframework.data.domain.Page;

public record ShelterProfilePage(
        ShelterInfoDto shelter,
        PetProfileSlice petList
) {

    public static ShelterProfilePage of(final Shelter shelter,
                                        final Page<Pet> petPage) {
        return new ShelterProfilePage(
                ShelterInfoDto.of(shelter),
                PetProfileSlice.of(petPage)
        );
    }
}
