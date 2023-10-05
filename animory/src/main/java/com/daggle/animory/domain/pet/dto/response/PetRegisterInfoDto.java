package com.daggle.animory.domain.pet.dto.response;

import com.daggle.animory.domain.pet.dto.PetPolygonProfileDto;
import com.daggle.animory.domain.pet.entity.AdoptionStatus;
import com.daggle.animory.domain.pet.entity.NeutralizationStatus;
import com.daggle.animory.domain.pet.entity.Pet;
import com.daggle.animory.domain.pet.entity.PetPolygonProfile;
import com.daggle.animory.domain.pet.entity.PetType;
import com.daggle.animory.domain.pet.entity.Sex;
import com.daggle.animory.domain.pet.util.PetAgeToBirthDateConverter;
import java.time.LocalDate;
import lombok.Builder;

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
        NeutralizationStatus neutralizationStatus,
        LocalDate protectionExpirationDate,
        String description,
        String profileImageUrl,
        String profileShortFormUrl,
        PetPolygonProfileDto petPolygonProfileDto
) {

    public static PetRegisterInfoDto fromEntity(Pet registerPet,
            PetPolygonProfile petPolygonProfile) {
        return PetRegisterInfoDto.builder()
                .name(registerPet.getName())
                .age(PetAgeToBirthDateConverter.birthDateToAge(registerPet.getBirthDate()))
                .type(registerPet.getType())
                .weight(registerPet.getWeight())
                .size(registerPet.getSize())
                .sex(registerPet.getSex())
                .vaccinationStatus(registerPet.getVaccinationStatus())
                .adoptionStatus(registerPet.getAdoptionStatus())
                .neutralizationStatus(registerPet.getNeutralizationStatus())
                .protectionExpirationDate(registerPet.getProtectionExpirationDate())
                .description(registerPet.getDescription())
                .profileImageUrl(registerPet.getProfileImageUrl())
                .profileShortFormUrl(registerPet.getProfileShortFormUrl())
                .petPolygonProfileDto(PetPolygonProfileDto.fromEntity(petPolygonProfile))
                .build();
    }

}

