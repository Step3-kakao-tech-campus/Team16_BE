package com.daggle.animory.domain.account.dto.request;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public record EmailValidateDto(
        @NotBlank(message = "이메일을 입력해주세요.")
        @Email(message = "이메일 형식에 맞지 않습니다.") String email) {
}
