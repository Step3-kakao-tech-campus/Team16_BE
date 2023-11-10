package com.daggle.animory.domain.shortform.exception;

public class ShortFormNotFoundException extends RuntimeException {
    @Override
    public String getMessage() {
        return ShortFormExceptionMessage.SHORT_FORM_NOT_FOUND.getMessage();
    }
}
