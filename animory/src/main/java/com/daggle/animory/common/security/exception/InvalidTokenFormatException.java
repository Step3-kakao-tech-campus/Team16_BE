package com.daggle.animory.common.security.exception;

public class InvalidTokenFormatException extends RuntimeException {
    @Override
    public String getMessage() {
        return SecurityExceptionMessage.INVALID_TOKEN_FORMAT.getMessage();
    }
}
