package com.daggle.animory.domain.pet.dto.request;

import com.daggle.animory.domain.pet.dto.PetPolygonProfileDto;
import com.daggle.animory.domain.pet.entity.AdoptionStatus;
import com.daggle.animory.domain.pet.entity.NeutralizationStatus;
import com.daggle.animory.domain.pet.entity.Pet;
import com.daggle.animory.domain.pet.entity.PetType;
import com.daggle.animory.domain.pet.entity.Sex;
import com.daggle.animory.domain.pet.util.PetAgeToBirthDateConverter;
import com.daggle.animory.domain.shelter.entity.Shelter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;

public record PetRegisterRequestDto(
        @NotNull(message = "이름을 입력해주세요.") String name,
        @NotNull(message = "나이를 입력해주세요.") String age,
        @NotNull(message = "종을 입력해주세요.") PetType type,
        float weight,
        String size,
        @NotNull(message = "성별을 입력해주세요.") Sex sex,
        String vaccinationStatus,
        @NotNull(message = "입양 상태를 입력해주세요.") AdoptionStatus adoptionStatus,
        NeutralizationStatus neutralizationStatus,
        LocalDate protectionExpirationDate,
        String description,
        PetPolygonProfileDto petPolygonProfileDto
) {

    public Pet toEntity(Shelter shelter, String imageUrl, String videoUrl) {
        return Pet.builder()
                .name(name)
                .birthDate(PetAgeToBirthDateConverter.ageToBirthDate(age))
                .type(type)
                .weight(weight)
                .sex(sex)
                .description(description)
                .protectionExpirationDate(protectionExpirationDate)
                .vaccinationStatus(vaccinationStatus)
                .neutralizationStatus(neutralizationStatus)
                .adoptionStatus(adoptionStatus)
                .profileImageUrl(imageUrl)
                .profileShortFormUrl(videoUrl)
                .size(size)
                .createdAt(LocalDateTime.now())
                .shelter(shelter)
                .build();
    }
}
