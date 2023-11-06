package com.daggle.animory.domain.shortform.dto.response;

import com.daggle.animory.domain.pet.entity.PetVideo;

import java.util.List;

public record CategoryShortFormPage(

    List<ShortFormDto> shortForms,
    boolean hasNext,
    Integer nextPage
) {

    public static CategoryShortFormPage of(final List<PetVideo> petVideos,
                                           final boolean hasNext,
                                           final Integer nextPage) {
        return new CategoryShortFormPage(
            petVideos.stream()
                .map(ShortFormDto::of)
                .toList(),
            hasNext,
            nextPage
        );
    }
}

