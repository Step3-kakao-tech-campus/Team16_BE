package com.daggle.animory.domain.shelter.dto.response;

import com.daggle.animory.domain.pet.entity.AdoptionStatus;
import com.daggle.animory.domain.pet.entity.Pet;
import com.daggle.animory.domain.pet.util.PetAgeToBirthDateConverter;
import lombok.Builder;

@Builder
public record PetProfileDto(
    Integer id,
    String name,

    String age,

    String profileImageUrl,
    AdoptionStatus adoptionStatus
) {

    public static PetProfileDto of(final Pet pet) {
        return PetProfileDto.builder()
            .id(pet.getId())
            .name(pet.getName())
            .age(PetAgeToBirthDateConverter.birthDateToAge(pet.getBirthDate()))
            .profileImageUrl(pet.getProfileImageUrl())
            .adoptionStatus(pet.getAdoptionStatus())
            .build();
    }
}
