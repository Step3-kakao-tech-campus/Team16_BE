package com.daggle.animory.domain.pet.dto.response;

import com.daggle.animory.domain.pet.entity.AdoptionStatus;
import com.daggle.animory.domain.pet.entity.Pet;
import com.daggle.animory.domain.pet.util.PetAgeToBirthDateConverter;
import lombok.Builder;

@Builder
public record NewPetDto(
        Integer petId,
        String petName,
        String petAge,
        String profileImageUrl,
        AdoptionStatus adoptionStatus,
        Integer shelterId,
        String shelterName
) {

    public static NewPetDto fromEntity(final Pet pet) {
        return NewPetDto.builder()
                .petId(pet.getId())
                .petName(pet.getName())
                .petAge(PetAgeToBirthDateConverter.birthDateToAge(pet.getBirthDate()))
                .profileImageUrl(pet.getProfileImageUrl())
                .adoptionStatus(pet.getAdoptionStatus())
                .shelterId(pet.getShelter().getId())
                .shelterName(pet.getShelter().getName())
                .build();
    }
}
