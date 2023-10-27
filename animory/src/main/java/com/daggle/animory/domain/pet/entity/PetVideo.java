package com.daggle.animory.domain.pet.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Entity
@AllArgsConstructor
@Table(name = "pet_video")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PetVideo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String videoUrl;

    @NotNull
    private int likeCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id")
    private Pet pet;


    @Builder
    public PetVideo(String videoUrl, Pet pet) {
        this.videoUrl = videoUrl;
        this.likeCount = 0;
        this.pet = pet;
    }

}
