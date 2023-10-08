package com.daggle.animory.domain.pet.dto.response;

import java.util.List;

public record PetProfilesDto(
    List<SosPetDto> sosList,
    List<NewPetDto> newList
) {
}
