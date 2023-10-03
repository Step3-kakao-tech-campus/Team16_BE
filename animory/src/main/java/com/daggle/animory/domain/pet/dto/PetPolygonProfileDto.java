package com.daggle.animory.domain.pet.dto;

import com.daggle.animory.domain.pet.entity.Pet;
import com.daggle.animory.domain.pet.entity.PetPolygonProfile;

public record PetPolygonProfileDto(
        int intelligence,
        int affinity,
        int athletic,
        int adaptability,
        int activeness
) {
    public PetPolygonProfile toEntity(Pet pet) {
        return PetPolygonProfile.builder()
                .pet(pet)
                .intelligence(intelligence)
                .affinity(affinity)
                .athletic(athletic)
                .adaptability(adaptability)
                .activeness(activeness)
                .build();
    }
}
