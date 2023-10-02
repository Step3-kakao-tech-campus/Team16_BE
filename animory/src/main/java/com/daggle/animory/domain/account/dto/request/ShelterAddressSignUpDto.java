package com.daggle.animory.domain.account.dto.request;

import com.daggle.animory.domain.shelter.Province;
import lombok.Builder;

public record ShelterAddressSignUpDto(Province province,
                                      String city,
                                      String roadName,
                                      String detail) {
    @Builder
    public ShelterAddressSignUpDto {
    }
}
