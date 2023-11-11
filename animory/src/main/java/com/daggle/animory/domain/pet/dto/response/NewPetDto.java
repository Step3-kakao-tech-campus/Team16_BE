package com.daggle.animory.domain.pet.dto.response;

import com.daggle.animory.domain.pet.entity.Pet;
import com.daggle.animory.domain.pet.entity.Sex;
import com.daggle.animory.domain.pet.util.PetAgeToBirthDateConverter;
import lombok.Builder;

@Builder
public record NewPetDto(
    Integer petId,
    String petName,
    String petAge,
    Sex sex,
    String profileImageUrl,
    String adoptionStatus,
    Integer shelterId,
    String shelterName
) {

    public static NewPetDto fromEntity(final Pet pet) {
        return NewPetDto.builder()
            .petId(pet.getId())
            .petName(pet.getName())
            .petAge(PetAgeToBirthDateConverter.birthDateToAge(pet.getBirthDate()))
            .sex(pet.getSex())
            .profileImageUrl(pet.getProfileImageUrl())
            .adoptionStatus(pet.getAdoptionStatus().getMessage())
            .shelterId(pet.getShelter().getId())
            .shelterName(pet.getShelter().getName())
            .build();
    }
}
