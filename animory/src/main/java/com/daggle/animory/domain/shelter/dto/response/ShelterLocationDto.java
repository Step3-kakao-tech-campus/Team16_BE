package com.daggle.animory.domain.shelter.dto.response;

import com.daggle.animory.domain.shelter.entity.Shelter;
import lombok.Getter;

@Getter
public record ShelterLocationDto(
    Integer id,
    String name,
    String contact,
    Integer kakaoLocationId,
    double x,
    double y

) {

    public static ShelterLocationDto of(final Shelter shelter) {
        return new ShelterLocationDto(
            shelter.getId(),
            shelter.getName(),
            shelter.getContact(),
            shelter.getAddress().getKakaoLocationId(),
            shelter.getAddress().getX(),
            shelter.getAddress().getY()
        );
    }
}
