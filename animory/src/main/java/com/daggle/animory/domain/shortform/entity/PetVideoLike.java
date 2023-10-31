package com.daggle.animory.domain.shortform.entity;

import com.daggle.animory.common.entity.BaseEntity;
import com.daggle.animory.domain.pet.entity.PetVideo;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "pet_video_like")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PetVideoLike extends BaseEntity {

    @Id
    @GeneratedValue
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
