package com.daggle.animory.domain.shortform.dto.response;

import com.daggle.animory.domain.pet.entity.Pet;
import org.springframework.data.domain.Slice;

import java.util.List;

public record CategoryShortFormPage(

    String categoryTitle,
    List<ShortFormDto> shortForms,
    boolean hasNext
) {

    public static CategoryShortFormPage of(final String categoryTitle,
                                           final Slice<Pet> petSlice) {
        return new CategoryShortFormPage(
            categoryTitle,
            petSlice.getContent().stream().map(ShortFormDto::of).toList(),
            petSlice.hasNext()
        );
    }
}

