package com.daggle.animory.domain.shelter.dto.response;

import com.daggle.animory.domain.pet.entity.AdoptionStatus;
import lombok.Builder;

@Builder
public record PetProfileDto(
        Integer id,
        String name,
        AdoptionStatus adoptionStatus
) {
}
