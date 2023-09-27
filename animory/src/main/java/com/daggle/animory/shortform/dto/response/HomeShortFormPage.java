package com.daggle.animory.shortform.dto.response;

import java.util.List;

public record HomeShortFormPage (
    List<ShortFormDto> shortForms,
    boolean hasNext
){

}
