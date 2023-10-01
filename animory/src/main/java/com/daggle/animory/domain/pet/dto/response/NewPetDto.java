package com.daggle.animory.domain.pet.dto.response;

import com.daggle.animory.domain.pet.entity.AdoptionStatus;

public record NewPetDto(
    Integer petId,
    String petName,
    String petAge,
    String profileImageUrl,
    AdoptionStatus adoptionStatus,
    Integer shelterId,
    String shelterName
) {
}
