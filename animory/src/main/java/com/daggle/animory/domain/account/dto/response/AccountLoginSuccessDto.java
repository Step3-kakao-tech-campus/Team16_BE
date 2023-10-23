package com.daggle.animory.domain.account.dto.response;

import lombok.Builder;

import java.util.Date;

@Builder
public record AccountLoginSuccessDto(
    Integer accountId,
    AccountInfo accountInfo,

    Date tokenExpirationDateTime
) {
}
