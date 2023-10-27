package com.daggle.animory.domain.pet.entity;

import com.daggle.animory.common.entity.BaseEntity;
import com.daggle.animory.domain.pet.dto.request.PetUpdateRequestDto;
import com.daggle.animory.domain.pet.util.PetAgeToBirthDateConverter;
import com.daggle.animory.domain.shelter.entity.Shelter;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter
@Entity
@Table(name = "pet")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pet extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate birthDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PetType type;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Sex sex;

    private float weight;

    @Column(length = 1000)
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate protectionExpirationDate;

    private String vaccinationStatus;

    @Enumerated(EnumType.STRING)
    private NeutralizationStatus neutralizationStatus;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AdoptionStatus adoptionStatus;

    private String size;

    @NotNull
    private String profileImageUrl;

    @Getter(AccessLevel.NONE) // 여러 개의 Pet Video를 가지는 UseCase가 정의되고 나면 사용하기 위해, 실수 방지 차원에서 접근을 제거해두겠습니다.
    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PetVideo> petVideos;

    @Embedded
    private PetPolygonProfile petPolygonProfile;

    @ManyToOne
    @JoinColumn(name = "shelter_id")
    private Shelter shelter;

    @Builder
    public Pet(final String name, final LocalDate birthDate, final PetType type, final float weight,
               final Sex sex, final String description, final LocalDate protectionExpirationDate,
               final String vaccinationStatus, final NeutralizationStatus neutralizationStatus,
               final AdoptionStatus adoptionStatus, final String profileImageUrl, final List<PetVideo> petVideos,
               final String size, final PetPolygonProfile petPolygonProfile, final Shelter shelter) {
        this.name = name;
        this.birthDate = birthDate;
        this.type = type;
        this.weight = weight;
        this.sex = sex;
        this.description = description;
        this.protectionExpirationDate = protectionExpirationDate;
        this.vaccinationStatus = vaccinationStatus;
        this.neutralizationStatus = neutralizationStatus;
        this.adoptionStatus = adoptionStatus;
        this.profileImageUrl = profileImageUrl;
        this.petVideos = petVideos;
        petVideos.forEach(petVideo -> petVideo.setPet(this));
        this.size = size;
        this.petPolygonProfile = petPolygonProfile;
        this.shelter = shelter;
    }


    public void setAdopted() {
        this.adoptionStatus = AdoptionStatus.YES;
        this.protectionExpirationDate = null;
    }

    public void updateInfo(final PetUpdateRequestDto petUpdateRequestDto) {
        this.name = petUpdateRequestDto.name();
        this.birthDate = PetAgeToBirthDateConverter.ageToBirthDate(petUpdateRequestDto.age());
        this.type = petUpdateRequestDto.type();
        this.weight = petUpdateRequestDto.weight();
        this.size = petUpdateRequestDto.size();
        this.sex = petUpdateRequestDto.sex();
        this.vaccinationStatus = petUpdateRequestDto.vaccinationStatus();
        this.neutralizationStatus = petUpdateRequestDto.neutralizationStatus();
        this.adoptionStatus = petUpdateRequestDto.adoptionStatus();
        this.protectionExpirationDate = petUpdateRequestDto.protectionExpirationDate();
        this.petPolygonProfile = petUpdateRequestDto.petPolygonProfileDto().toEntity();
        this.description = petUpdateRequestDto.description();
    }

    // 현재는 UX 상 Pet은 1개의 동영상만 가지고 있는다.
    public PetVideo getPetVideo() {
        return petVideos.get(0);
    }


}
