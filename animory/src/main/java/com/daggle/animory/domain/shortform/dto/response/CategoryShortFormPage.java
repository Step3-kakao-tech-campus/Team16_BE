package com.daggle.animory.domain.shortform.dto.response;

import com.daggle.animory.domain.pet.entity.PetVideo;

import java.util.List;

public record CategoryShortFormPage(

    String categoryTitle,
    List<ShortFormDto> shortForms,
    boolean hasNext
) {

    public static CategoryShortFormPage of(final String categoryTitle,
                                           final List<PetVideo> petVideos,
                                           final boolean hasNext) {
        return new CategoryShortFormPage(
            categoryTitle,
            petVideos.stream()
                .map(ShortFormDto::of)
                .toList(),
            hasNext
        );
    }
}

