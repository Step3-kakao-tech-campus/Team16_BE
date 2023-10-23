package com.daggle.animory.domain.shelter.exception;

// TODO: 예외처리
public class ShelterAlreadyExistException extends RuntimeException {
    @Override
    public String getMessage() {
        return ShelterExceptionMessage.SHELTER_ALREADY_EXIST.getMessage();
    }
}
