package com.daggle.animory.domain.pet.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdatePetSuccessDto {
    private final String message = "수정이 완료되었습니다.";
    private final Integer petId;

    @Builder
    public UpdatePetSuccessDto(final Integer petId) {
        this.petId = petId;
    }

}
