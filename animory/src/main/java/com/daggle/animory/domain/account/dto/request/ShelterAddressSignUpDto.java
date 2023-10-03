package com.daggle.animory.domain.account.dto.request;

import com.daggle.animory.domain.shelter.entity.Province;
import lombok.Builder;
@Builder
public record ShelterAddressSignUpDto(Province province,
                                      String city,
                                      String roadName,
                                      String detail) {
}
