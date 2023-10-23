package com.daggle.animory.domain.shortform.dto.request;


import com.daggle.animory.domain.pet.entity.PetType;
import com.daggle.animory.domain.shelter.entity.Province;

public record ShortFormSearchCondition(
    PetType type,
    Province area
) {
}