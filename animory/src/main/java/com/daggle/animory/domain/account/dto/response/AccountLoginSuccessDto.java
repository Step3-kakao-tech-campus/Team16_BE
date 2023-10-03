package com.daggle.animory.domain.account.dto.response;

import com.daggle.animory.domain.account.entity.AccountRole;

public record AccountLoginSuccessDto(Integer Id, AccountRole accountRole) {
}
