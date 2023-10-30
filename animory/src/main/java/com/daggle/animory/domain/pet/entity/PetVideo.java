package com.daggle.animory.domain.pet.entity;

import com.daggle.animory.common.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Entity
@Table(name = "pet_video")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PetVideo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String videoUrl;

    @NotNull
    private int likeCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id")
    @JsonBackReference
    private Pet pet;


    @Builder
    public PetVideo(String videoUrl, Pet pet) {
        this.videoUrl = videoUrl;
        this.likeCount = 0;
        this.pet = pet;
    }

    public void setPet(final Pet pet) {
        this.pet = pet;
    }

    public void updateLikeCount() {
        this.likeCount++;
    }

    public void deleteLikeCount() {
        this.likeCount--;
    }
}
