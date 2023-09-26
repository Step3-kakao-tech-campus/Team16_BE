package com.daggle.animory.shortform.dto.response;

import com.daggle.animory.model.pet.AdoptionStatus;

import java.util.List;

public record ShortFormPage(

    String categoryTitle,
    List<ShortFormDto> shortForms,
    boolean hasNext
) {
}

record ShortFormDto(
    Integer petId,
    String name,
    String age,
    Integer shelterId,
    String shelterName,
    String profileShortFormUrl,
    AdoptionStatus adoptionStatus

) {
}
