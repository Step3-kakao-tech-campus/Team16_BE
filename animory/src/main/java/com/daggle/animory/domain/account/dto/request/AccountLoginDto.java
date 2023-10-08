package com.daggle.animory.domain.account.dto.request;

import lombok.Builder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Builder
public record AccountLoginDto(
        @Email @NotNull(message = "이메일을 입력해주세요.") String email,
        @NotNull(message = "비밀번호를 입력해주세요.") String password) {
}
