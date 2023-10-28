package com.daggle.animory.domain.account.dto;

public record TokenWithExpirationDateTimeDto(
    String token,
    java.util.Date expirationDateTime
) {


}
