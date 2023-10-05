package com.daggle.animory.domain.account.dto.response;

import com.daggle.animory.domain.account.entity.AccountRole;
import lombok.Builder;

@Builder
public record AccountLoginSuccessDto(Integer id, AccountRole accountRole) {
}
