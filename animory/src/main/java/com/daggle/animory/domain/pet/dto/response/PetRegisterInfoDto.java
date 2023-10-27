package com.daggle.animory.domain.pet.dto.response;

import com.daggle.animory.domain.pet.dto.PetPolygonProfileDto;
import com.daggle.animory.domain.pet.entity.*;
import com.daggle.animory.domain.pet.util.PetAgeToBirthDateConverter;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record PetRegisterInfoDto(
        String name,
        String age,
        PetType type,
        float weight,
        String size,
        Sex sex,
        String vaccinationStatus,
        AdoptionStatus adoptionStatus,
        String neutralizationStatus,
        LocalDate protectionExpirationDate,
        String description,
        String profileImageUrl,
        String profileShortFormUrl,
        PetPolygonProfileDto petPolygonProfileDto
) {

    public static PetRegisterInfoDto fromEntity(final Pet registerPet) {
        return PetRegisterInfoDto.builder()
                .name(registerPet.getName())
                .age(PetAgeToBirthDateConverter.birthDateToAge(registerPet.getBirthDate()))
                .type(registerPet.getType())
                .weight(registerPet.getWeight())
                .size(registerPet.getSize())
                .sex(registerPet.getSex())
                .vaccinationStatus(registerPet.getVaccinationStatus())
                .adoptionStatus(registerPet.getAdoptionStatus())
                .neutralizationStatus(registerPet.getNeutralizationStatus().getMessage())
                .protectionExpirationDate(registerPet.getProtectionExpirationDate())
                .description(registerPet.getDescription())
                .profileImageUrl(registerPet.getProfileImageUrl())
                .profileShortFormUrl(registerPet.getProfileShortFormUrl())
                .petPolygonProfileDto(PetPolygonProfileDto.fromEntity(registerPet.getPetPolygonProfile()))
                .build();
    }

}

