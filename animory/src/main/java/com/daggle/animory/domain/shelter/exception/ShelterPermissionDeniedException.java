package com.daggle.animory.domain.shelter.exception;

public class ShelterPermissionDeniedException extends RuntimeException {
    @Override
    public String getMessage() {
        return ShelterExceptionMessage.SHELTER_PERMISSION_DENIED.getMessage();
    }
}
