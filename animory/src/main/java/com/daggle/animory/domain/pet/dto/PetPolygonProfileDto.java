package com.daggle.animory.domain.pet.dto;

import com.daggle.animory.domain.pet.entity.Pet;
import com.daggle.animory.domain.pet.entity.PetPolygonProfile;
import lombok.Builder;

@Builder
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

    public static PetPolygonProfileDto fromEntity(PetPolygonProfile petPolygonProfile) {
        return PetPolygonProfileDto.builder()
                .intelligence(petPolygonProfile.getIntelligence())
                .affinity(petPolygonProfile.getAffinity())
                .athletic(petPolygonProfile.getAthletic())
                .adaptability(petPolygonProfile.getAdaptability())
                .activeness(petPolygonProfile.getActiveness())
                .build();
    }
}
