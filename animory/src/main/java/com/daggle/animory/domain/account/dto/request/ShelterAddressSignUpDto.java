package com.daggle.animory.domain.account.dto.request;

import com.daggle.animory.domain.shelter.entity.Province;
import com.daggle.animory.domain.shelter.entity.ShelterAddress;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.Builder;

import javax.validation.constraints.NotNull;

@Builder
public record ShelterAddressSignUpDto(
    @NotNull(message = "광역시/도를 입력해주세요.") Province province,
    String city,
    @NotNull(message = "도로명을 입력해주세요.") String roadName,
    String detail) {

    @Hidden
    public ShelterAddress getShelterAddress(final String zonecode) {
        return ShelterAddress.builder()
            .province(province)
            .city(city)
            .roadName(roadName)
            .detail(detail)
            .zonecode(zonecode)
            .build();
    }
}
