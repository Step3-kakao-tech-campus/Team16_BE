package com.daggle.animory.domain.pet.exception;


public class PetPermissionDeniedException extends RuntimeException {
    @Override
    public String getMessage() {
        return PetExceptionMessage.PET_PERMISSION_DENIED.getMessage();
    }
}
