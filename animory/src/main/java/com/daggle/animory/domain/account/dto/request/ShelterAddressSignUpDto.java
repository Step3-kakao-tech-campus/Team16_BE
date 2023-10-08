package com.daggle.animory.domain.account.dto.request;

import com.daggle.animory.domain.shelter.entity.Province;
import com.daggle.animory.domain.shelter.entity.ShelterAddress;
import lombok.Builder;
@Builder
public record ShelterAddressSignUpDto(Province province,
                                      String city,
                                      String roadName,
                                      String detail) {
    public ShelterAddress getShelterAddress() {
        return ShelterAddress.builder()
                .province(province)
                .city(city)
                .roadName(roadName)
                .detail(detail)
                .build();
    }
}
