package com.daggle.animory.domain.shelter.dto.response;

import com.daggle.animory.domain.pet.entity.AdoptionStatus;
import com.daggle.animory.domain.pet.entity.Pet;
import lombok.Builder;

@Builder
public record PetProfileDto(
        Integer id,
        String name,
        AdoptionStatus adoptionStatus
) {

    public static PetProfileDto of(final Pet pet) {
        return PetProfileDto.builder()
                .id(pet.getId())
                .name(pet.getName())
                .adoptionStatus(pet.getAdoptionStatus())
                .build();
    }
}
