package com.daggle.animory.common.security.exception;

public class InvalidTokenFormatException extends RuntimeException {
    @Override
    public String getMessage() {
        return SecurityExceptionMessage.UNAUTHORIZED.getMessage();
    }
}
