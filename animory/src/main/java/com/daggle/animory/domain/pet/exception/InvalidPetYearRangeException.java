package com.daggle.animory.domain.pet.exception;

public class InvalidPetYearRangeException extends RuntimeException {
    private int year;
    public InvalidPetYearRangeException(final int year) {
        this.year = year;
    }

    @Override
    public String getMessage() {
        return PetExceptionMessage.INVALID_PET_YEAR_RANGE.getMessage() + year;
    }
}
