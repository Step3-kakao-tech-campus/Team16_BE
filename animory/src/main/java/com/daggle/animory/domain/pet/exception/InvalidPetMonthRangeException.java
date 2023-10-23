package com.daggle.animory.domain.pet.exception;

public class InvalidPetMonthRangeException extends RuntimeException {
    private int month;
    public InvalidPetMonthRangeException(final int month) {
        this.month = month;
    }

    @Override
    public String getMessage() {
        return PetExceptionMessage.INVALID_PET_YEAR_RANGE.getMessage() + month;
    }
}
