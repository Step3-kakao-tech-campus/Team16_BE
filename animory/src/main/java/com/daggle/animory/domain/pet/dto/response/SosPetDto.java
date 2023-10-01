package com.daggle.animory.domain.pet.dto.response;

import java.time.LocalDate;

public record SosPetDto(
    Integer petId,
    String petName,
    String petAge,
    String profileImageUrl,
    LocalDate protectionExpirationDate,
    Integer shelterId,
    String shelterName
) {
}
