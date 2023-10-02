package com.daggle.animory.domain.account.dto.response;

import com.daggle.animory.domain.account.AccountRole;

public record AccountLoginSuccessDto(Integer Id, AccountRole accountRole) {
}
