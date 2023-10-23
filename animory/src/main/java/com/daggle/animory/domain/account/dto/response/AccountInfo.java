package com.daggle.animory.domain.account.dto.response;

import com.daggle.animory.domain.account.entity.AccountRole;
import lombok.Builder;

@Builder
public record AccountInfo(
    Integer id,
    AccountRole role
) {
}
