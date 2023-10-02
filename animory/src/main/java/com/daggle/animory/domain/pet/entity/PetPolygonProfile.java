package com.daggle.animory.domain.pet.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@Table(name = "pet_polygon_profile")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PetPolygonProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @MapsId
    @OneToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    private int intelligence;


    private int affinity;

    private int athletic;

    private int adaptability;

    private int activeness;

}