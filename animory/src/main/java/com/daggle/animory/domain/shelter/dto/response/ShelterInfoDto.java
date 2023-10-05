package com.daggle.animory.domain.shelter.dto.response;

import com.daggle.animory.domain.shelter.entity.Shelter;
import com.daggle.animory.domain.shelter.entity.ShelterAddress;
import lombok.Builder;

@Builder
public record ShelterInfoDto(
        Integer id,
        String name,
        ShelterAddress address,
        String contact
) {
    public static ShelterInfoDto of(final Shelter shelter) {
        return ShelterInfoDto.builder()
                .id(shelter.getId())
                .name(shelter.getName())
                .address(shelter.getAddress())
                .contact(shelter.getContact())
                .build();
    }
}
