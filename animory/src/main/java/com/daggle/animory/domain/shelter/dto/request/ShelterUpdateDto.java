package com.daggle.animory.domain.shelter.dto.request;

import lombok.Builder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
@Builder
public record ShelterUpdateDto(
        @NotNull(message = "보호소 이름을 입력해주세요.") String name,
        @NotNull(message = "보호소 연락처를 입력해주세요.") String contact,
        @Valid @NotNull(message = "보호소 주소를 입력해주세요.") ShelterAddressUpdateDto shelterAddressUpdateDto) {
}
