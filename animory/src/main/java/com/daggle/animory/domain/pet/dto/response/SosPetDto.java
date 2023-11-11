package com.daggle.animory.domain.pet.dto.response;

import com.daggle.animory.domain.pet.entity.Pet;
import com.daggle.animory.domain.pet.entity.Sex;
import com.daggle.animory.domain.pet.util.PetAgeToBirthDateConverter;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record SosPetDto(
    Integer petId,
    String petName,
    String petAge,
    Sex sex,
    String profileImageUrl,
    LocalDate protectionExpirationDate,
    Integer shelterId,
    String shelterName
) {

    public static SosPetDto fromEntity(final Pet pet) {
        return SosPetDto.builder()
            .petId(pet.getId())
            .petName(pet.getName())
            .petAge(PetAgeToBirthDateConverter.birthDateToAge(pet.getBirthDate()))
            .sex(pet.getSex())
            .profileImageUrl(pet.getProfileImageUrl())
            .protectionExpirationDate(pet.getProtectionExpirationDate())
            .shelterId(pet.getShelter().getId())
            .shelterName(pet.getShelter().getName())
            .build();
    }
}
