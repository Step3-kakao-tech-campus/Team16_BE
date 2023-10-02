package com.daggle.animory.domain.account.dto.request;

import java.util.Objects;

public record EmailValidateDto(String email) {
    public EmailValidateDto {
        Objects.requireNonNull(email);
    }
}
