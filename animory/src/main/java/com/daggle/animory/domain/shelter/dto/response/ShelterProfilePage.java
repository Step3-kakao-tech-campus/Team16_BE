package com.daggle.animory.domain.shelter.dto.response;

public record ShelterProfilePage(
        ShelterInfoDto shelter,
        PetProfileSlice petList
) {
}
