package com.daggle.animory.domain.shortform.exception;

public enum ShortFormExceptionMessage {
    SHORT_FORM_NOT_FOUND("해당하는 숏폼이 존재하지 않습니다."),
    ALREADY_LIKED_PET_VIDEO("이미 좋아요를 누른 영상입니다."),
    NOT_LIKED_PET_VIDEO("좋아요를 누르지 않은 영상입니다.");
    private final String message;

    ShortFormExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
