package com.daggle.animory.domain.shortform.dto.response;

import com.daggle.animory.domain.pet.entity.Pet;
import org.springframework.data.domain.Slice;

import java.util.List;

public record HomeShortFormPage (
    List<ShortFormDto> shortForms,
    boolean hasNext
){

    public static HomeShortFormPage of(final Slice<Pet> petSlice) {
        return new HomeShortFormPage(
            petSlice.getContent().stream().map(ShortFormDto::of).toList(),
            petSlice.hasNext()
        );
    }
}
