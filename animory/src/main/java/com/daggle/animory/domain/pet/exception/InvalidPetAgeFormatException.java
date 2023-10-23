package com.daggle.animory.domain.pet.exception;

public class InvalidPetAgeFormatException extends RuntimeException {
    private String age;
    public InvalidPetAgeFormatException(final String age) {
        this.age = age;
    }

    @Override
    public String getMessage() {
        return PetExceptionMessage.INVALID_PET_AGE_FORMAT.getMessage() + age;
    }
}
