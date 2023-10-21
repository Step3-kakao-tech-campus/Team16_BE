package com.daggle.animory.common.security.exception;

public class ForbiddenException extends RuntimeException {
    @Override
    public String getMessage() {
        return SecurityExceptionMessage.FORBIDDEN.getMessage();
    }
}
