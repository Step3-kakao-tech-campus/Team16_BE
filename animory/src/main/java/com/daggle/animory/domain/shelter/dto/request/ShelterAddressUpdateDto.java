package com.daggle.animory.domain.shelter.dto.request;

import com.daggle.animory.domain.shelter.entity.Province;
import lombok.Builder;

import javax.validation.constraints.NotNull;

@Builder
public record ShelterAddressUpdateDto(
    @NotNull(message = "광역시/도를 입력해주세요.") Province province,
    String city,
    @NotNull(message = "도로명을 입력해주세요.") String roadName,
    String detail,
    String zonecode) {


}
