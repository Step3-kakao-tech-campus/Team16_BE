package com.daggle.animory.domain.account.exception;

public class AlreadyExistEmailException extends RuntimeException {
    @Override
    public String getMessage() {
        return AccountExceptionMessage.ALREADY_EXIST_EMAIL.getMessage();
    }
}
