package com.daggle.animory.domain.shelter.dto.response;

import com.daggle.animory.domain.shelter.entity.ShelterAddress;
import lombok.Builder;

@Builder
public record ShelterInfoDto(
        Integer id,
        String name,
        ShelterAddress address,
        String contact
) {
}
