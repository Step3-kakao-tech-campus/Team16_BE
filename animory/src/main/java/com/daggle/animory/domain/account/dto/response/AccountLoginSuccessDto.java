package com.daggle.animory.domain.account.dto.response;

import com.daggle.animory.domain.account.entity.AccountRole;
import lombok.Builder;

import java.util.Date;

@Builder
public record AccountLoginSuccessDto(
    Integer id,
    AccountRole accountRole,

    Date tokenExpirationDateTime
) {
}
