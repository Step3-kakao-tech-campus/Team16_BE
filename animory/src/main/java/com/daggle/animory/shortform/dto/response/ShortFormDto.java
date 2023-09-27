package com.daggle.animory.shortform.dto.response;

import com.daggle.animory.model.pet.AdoptionStatus;

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
