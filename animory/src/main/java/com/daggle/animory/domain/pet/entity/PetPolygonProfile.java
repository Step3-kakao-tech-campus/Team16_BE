package com.daggle.animory.domain.pet.entity;

import com.daggle.animory.domain.pet.dto.PetPolygonProfileDto;
import lombok.*;

import javax.persistence.*;

@Embeddable
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PetPolygonProfile {

    private int intelligence;

    private int affinity;

    private int athletic;

    private int adaptability;

    private int activeness;

    public void update(final PetPolygonProfileDto petPolygonProfileDto) {
        this.intelligence = petPolygonProfileDto.intelligence();
        this.affinity = petPolygonProfileDto.affinity();
        this.athletic = petPolygonProfileDto.athletic();
        this.adaptability = petPolygonProfileDto.adaptability();
        this.activeness = petPolygonProfileDto.activeness();
    }
}