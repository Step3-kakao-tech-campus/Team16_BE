package com.daggle.animory.domain.shortform.dto.response;

import com.daggle.animory.domain.pet.entity.AdoptionStatus;

public record ShortFormDto(
    Integer petId,
    String name,
    String age,
    Integer shelterId,
    String shelterName,
    String profileShortFormUrl,
    AdoptionStatus adoptionStatus

) {
}
