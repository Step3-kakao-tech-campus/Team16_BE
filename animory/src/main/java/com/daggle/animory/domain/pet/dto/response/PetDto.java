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


    public static PetDto fromEntity(Pet pet, PetPolygonProfile petPolygonProfile) {
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
                .petPolygonProfileDto(PetPolygonProfileDto.fromEntity(petPolygonProfile))
                .build();
    }
}
