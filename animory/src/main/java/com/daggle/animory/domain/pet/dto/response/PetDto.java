package com.daggle.animory.domain.pet.dto.response;

import com.daggle.animory.domain.pet.dto.PetPolygonProfileDto;
import com.daggle.animory.domain.pet.entity.*;
import com.daggle.animory.domain.pet.util.PetAgeToBirthDateConverter;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record PetDto(
        ShelterInfoDto shelterInfo,
        String name,
        String age,
        PetType type,
        Sex sex,
        float weight,
        String description,
        LocalDate protectionExpirationDate,
        String vaccinationStatus,
        NeutralizationStatus neutralizationStatus,
        AdoptionStatus adoptionStatus,
        String profileImageUrl,
        String size,
        PetPolygonProfileDto petPolygonProfileDto

) {


    public static PetDto fromEntity(final Pet pet) {
        return PetDto.builder()
                .shelterInfo(ShelterInfoDto.fromEntity(pet.getShelter()))
                .name(pet.getName())
                .age(PetAgeToBirthDateConverter.birthDateToAge(pet.getBirthDate()))
                .type(pet.getType())
                .sex(pet.getSex())
                .weight(pet.getWeight())
                .description(pet.getDescription())
                .protectionExpirationDate(pet.getProtectionExpirationDate())
                .vaccinationStatus(pet.getVaccinationStatus())
                .neutralizationStatus(pet.getNeutralizationStatus())
                .adoptionStatus(pet.getAdoptionStatus())
                .profileImageUrl(pet.getProfileImageUrl())
                .size(pet.getSize())
                .petPolygonProfileDto(PetPolygonProfileDto.fromEntity(pet.getPetPolygonProfile()))
                .build();
    }
}
