package com.daggle.animory.domain.pet.dto.request;

import com.daggle.animory.domain.pet.dto.PetPolygonProfileDto;
import com.daggle.animory.domain.pet.entity.AdoptionStatus;
import com.daggle.animory.domain.pet.entity.NeutralizationStatus;
import com.daggle.animory.domain.pet.entity.PetType;
import com.daggle.animory.domain.pet.entity.Sex;

import java.util.Optional;

public record PetUpdateRequestDto(
    Optional<String> name,
    Optional<String> age,
    Optional<PetType> type,
    Optional<Float> weight,
    Optional<String> size,
    Optional<Sex> sex,
    Optional<String> vaccinationStatus,
    Optional<AdoptionStatus> adoptionStatus,
    Optional<NeutralizationStatus> neutralizationStatus,
    Optional<String> description,
    Optional<PetPolygonProfileDto> petPolygonProfileDto
) {
}
