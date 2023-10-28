package com.daggle.animory.domain.pet.exception;

public class PetNotFoundException extends RuntimeException {
    @Override
    public String getMessage() {
        return PetExceptionMessage.PET_NOT_FOUND.getMessage();
    }
}
