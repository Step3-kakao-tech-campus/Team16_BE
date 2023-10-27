package com.daggle.animory.domain.pet.entity;

import com.daggle.animory.common.entity.BaseEntity;
import com.daggle.animory.domain.pet.dto.request.PetUpdateRequestDto;
import com.daggle.animory.domain.pet.util.PetAgeToBirthDateConverter;
import com.daggle.animory.domain.shelter.entity.Shelter;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Entity
@Builder
@AllArgsConstructor
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

    @NotNull
    private String profileImageUrl;

    @NotNull
    private String profileShortFormUrl;

    private String size;

    @Embedded
    private PetPolygonProfile petPolygonProfile;

    @ManyToOne
    @JoinColumn(name = "shelter_id")
    private Shelter shelter;

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
}
