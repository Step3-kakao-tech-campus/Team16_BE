package com.daggle.animory.domain.shortform.dto.response;

import com.daggle.animory.domain.pet.entity.AdoptionStatus;
import com.daggle.animory.domain.pet.entity.Pet;
import com.daggle.animory.domain.pet.util.PetAgeToBirthDateConverter;
import lombok.Builder;

@Builder
public record ShortFormDto(
    Integer petId,
    String name,
    String age,
    Integer shelterId,
    String shelterName,
    String profileShortFormUrl,
    AdoptionStatus adoptionStatus

) {
    public static ShortFormDto of(final Pet pet) {
        return ShortFormDto.builder()
            .petId(pet.getId())
            .name(pet.getName())
            .age(PetAgeToBirthDateConverter.birthDateToAge(pet.getBirthDate()))
            .shelterId(pet.getShelter().getId())
            .shelterName(pet.getShelter().getName())
            .profileShortFormUrl(pet.getProfileShortFormUrl())
            .adoptionStatus(pet.getAdoptionStatus())
            .build();
    }
}
