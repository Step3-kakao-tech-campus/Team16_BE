package com.daggle.animory.domain.account.exception;

public class CheckEmailOrPasswordException extends RuntimeException {
    @Override
    public String getMessage() {
        return AccountExceptionMessage.CHECK_EMAIL_OR_PASSWORD.getMessage();
    }
}
