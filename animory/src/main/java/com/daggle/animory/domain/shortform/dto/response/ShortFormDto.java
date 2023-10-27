package com.daggle.animory.domain.shortform.dto.response;

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
    String adoptionStatus

) {
    public static ShortFormDto of(final Pet pet) {
        return ShortFormDto.builder()
            .petId(pet.getId())
            .name(pet.getName())
            .age(PetAgeToBirthDateConverter.birthDateToAge(pet.getBirthDate()))
            .shelterId(pet.getShelter().getId())
            .shelterName(pet.getShelter().getName())
            .profileShortFormUrl(pet.getPetVideo().getVideoUrl())
            .adoptionStatus(pet.getAdoptionStatus().getMessage())
            .build();
    }
}
