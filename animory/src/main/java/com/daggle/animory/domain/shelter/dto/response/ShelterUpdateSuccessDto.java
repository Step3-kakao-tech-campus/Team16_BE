package com.daggle.animory.domain.shelter.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ShelterUpdateSuccessDto {
    private final String message = "수정이 완료되었습니다.";
    private final Integer shelterId;

    @Builder
    public ShelterUpdateSuccessDto(final Integer shelterId) {
        this.shelterId = shelterId;
    }
}
