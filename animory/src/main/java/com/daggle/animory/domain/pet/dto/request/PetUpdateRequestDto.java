package com.daggle.animory.domain.pet.dto.request;

import com.daggle.animory.domain.pet.dto.PetPolygonProfileDto;
import com.daggle.animory.domain.pet.entity.AdoptionStatus;
import com.daggle.animory.domain.pet.entity.NeutralizationStatus;
import com.daggle.animory.domain.pet.entity.PetType;
import com.daggle.animory.domain.pet.entity.Sex;
import lombok.Builder;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Builder
public record PetUpdateRequestDto(
        @NotNull(message = "이름을 입력해주세요.") String name,
        @NotNull(message = "나이를 입력해주세요.") String age,
        @NotNull(message = "종을 입력해주세요.") PetType type,
        float weight,
        String size,
        @NotNull(message = "성별을 입력해주세요.") Sex sex,
        String vaccinationStatus,
        @NotNull(message = "입양 상태를 입력해주세요.") AdoptionStatus adoptionStatus,
        NeutralizationStatus neutralizationStatus,
        String description,
        LocalDate protectionExpirationDate,
        PetPolygonProfileDto petPolygonProfileDto
) {

}
