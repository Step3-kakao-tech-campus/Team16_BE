package com.daggle.animory.domain.account.exception;

public enum AccountExceptionMessage {
    ALREADY_EXIST_EMAIL("이미 존재하는 이메일입니다."),
    CHECK_EMAIL_OR_PASSWORD("이메일 또는 비밀번호를 확인해주세요.");
    private final String message;

    AccountExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
