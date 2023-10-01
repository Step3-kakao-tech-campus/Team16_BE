package com.daggle.animory.domain.shortform.dto.request;


import com.daggle.animory.domain.shelter.Province;
import com.daggle.animory.domain.pet.entity.PetType;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.Min;

public record ShortFormSearchCondition(
    PetType type,
    Province area,

    @Min(0)
    int page) { // pageNumber


    public Pageable pageable() {
        return PageRequest.of(page, 10); // TODO: default page size 하드코딩되어 있음.
    }
}