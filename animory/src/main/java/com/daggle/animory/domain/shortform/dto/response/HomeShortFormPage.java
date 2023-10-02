package com.daggle.animory.domain.shortform.dto.response;

import java.util.List;

public record HomeShortFormPage (
    List<ShortFormDto> shortForms,
    boolean hasNext
){

}
