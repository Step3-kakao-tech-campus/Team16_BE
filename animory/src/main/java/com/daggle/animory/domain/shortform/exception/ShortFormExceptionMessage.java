package com.daggle.animory.domain.shortform.exception;

public enum ShortFormExceptionMessage {
    SHORT_FORM_NOT_FOUND("해당하는 숏폼이 존재하지 않습니다.");
    private final String message;

    ShortFormExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
