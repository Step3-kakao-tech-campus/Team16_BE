package com.daggle.animory.shortform.dto.response;

import java.util.List;

public record CategoryShortFormPage(

    String categoryTitle,
    List<ShortFormDto> shortForms,
    boolean hasNext
) {
}

