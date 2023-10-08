package com.daggle.animory.domain.pet.dto.response;

import com.daggle.animory.domain.shelter.entity.Shelter;
import lombok.Builder;

@Builder
public record ShelterInfoDto(
        int id,
        String name,
        String contact
) {

    public static ShelterInfoDto fromEntity(final Shelter shelter) {
        return ShelterInfoDto.builder()
                .id(shelter.getId())
                .name(shelter.getName())
                .contact(shelter.getContact())
                .build();
    }
}
