package com.daggle.animory.domain.shelter.exception;

public class ShelterNotFoundException extends RuntimeException {
    @Override
    public String getMessage() {
        return ShelterExceptionMessage.SHELTER_NOT_FOUND.getMessage();
    }
}
