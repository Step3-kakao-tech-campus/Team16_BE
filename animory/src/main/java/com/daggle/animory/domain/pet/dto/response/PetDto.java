package com.daggle.animory.domain.pet.dto.response;

import com.daggle.animory.domain.pet.dto.PetPolygonProfileDto;
import com.daggle.animory.domain.pet.entity.AdoptionStatus;
import com.daggle.animory.domain.pet.entity.NeutralizationStatus;
import com.daggle.animory.domain.pet.entity.PetType;
import com.daggle.animory.domain.pet.entity.Sex;

import java.time.LocalDate;

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


}
