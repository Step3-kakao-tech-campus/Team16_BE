package com.daggle.animory.domain.pet.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RegisterPetSuccessDto {

    private final String message = "등록이 완료되었습니다.";
    private final Integer petId;

    @Builder
    public RegisterPetSuccessDto(final Integer petId) {
        this.petId = petId;
    }

}
