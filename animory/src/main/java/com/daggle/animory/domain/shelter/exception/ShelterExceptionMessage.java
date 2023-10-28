package com.daggle.animory.domain.shelter.exception;


public enum ShelterExceptionMessage {
    SHELTER_NOT_FOUND("해당하는 보호소가 존재하지 않습니다."),
    SHELTER_ALREADY_EXIST("이미 존재하는 보호소입니다."),
    // TODO: 어디에 위치 있는 것이 맞는 건지?
    SHELTER_PERMISSION_DENIED("보호소 정보를 수정할 권한이 없습니다.");


    private final String message;

    ShelterExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
