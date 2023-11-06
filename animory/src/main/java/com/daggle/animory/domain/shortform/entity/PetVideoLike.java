package com.daggle.animory.domain.shortform.entity;

import com.daggle.animory.common.entity.BaseEntity;
import com.daggle.animory.domain.pet.entity.PetVideo;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Entity
@Table(name = "pet_video_like")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PetVideoLike extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String ipAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_video_id")
    private PetVideo petVideo;

    @Builder
    public PetVideoLike(final String ipAddress, final PetVideo petVideo) {
        this.ipAddress = ipAddress;
        this.petVideo = petVideo;
    }
}
