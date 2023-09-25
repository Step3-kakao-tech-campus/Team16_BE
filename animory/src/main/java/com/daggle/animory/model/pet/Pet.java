package com.daggle.animory.model.pet;

import com.daggle.animory.model.Shelter;
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
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private PetType type;

    @Column(columnDefinition = "DECIMAL(5,2)")
    private double weight;

    @Column(length = 1000)
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate protectionExpirationDate;

    private String vaccinationStatus;

    @Enumerated(EnumType.STRING)
    private NeutralizationStatus neutralizationStatus;

    @Enumerated(EnumType.STRING)
    private AdoptionStatus adoptionStatus;

    private String profileImageUrl;

    private String profileShortFormUrl;

    private String size;

    @ManyToOne
    @JoinColumn(name = "shelter_id")
    private Shelter shelter;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "pet")
    @JoinColumn(name = "pet_polygon_profile_id")
    private PetPolygonProfile petPolygonProfile;


}