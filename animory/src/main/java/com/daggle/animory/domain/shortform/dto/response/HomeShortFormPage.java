package com.daggle.animory.domain.shortform.dto.response;

import com.daggle.animory.domain.pet.entity.PetVideo;

import java.util.List;

public record HomeShortFormPage(
    List<ShortFormDto> shortForms,
    boolean hasNext,
    Integer nextPage
) {

    public static HomeShortFormPage of(final List<PetVideo> petVideos,
                                       final boolean hasNext,
                                       final Integer nextPage) {
        return new HomeShortFormPage(
            petVideos.stream()
                .map(ShortFormDto::of)
                .toList(),
            hasNext,
            nextPage
        );
    }
}
