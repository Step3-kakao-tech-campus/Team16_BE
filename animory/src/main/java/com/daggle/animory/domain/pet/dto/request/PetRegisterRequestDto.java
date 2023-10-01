package com.daggle.animory.domain.pet.dto.request;

import com.daggle.animory.domain.pet.dto.PetPolygonProfileDto;
import com.daggle.animory.domain.pet.entity.AdoptionStatus;
import com.daggle.animory.domain.pet.entity.NeutralizationStatus;
import com.daggle.animory.domain.pet.entity.PetType;
import com.daggle.animory.domain.pet.entity.Sex;

public record PetRegisterRequestDto(
    String name,
    String age,
    PetType type,
    float weight,
    String size,
    Sex sex,
    String vaccinationStatus,
    AdoptionStatus adoptionStatus,
    NeutralizationStatus neutralizationStatus,
    String description,
    PetPolygonProfileDto petPolygonProfileDto
) {
}
