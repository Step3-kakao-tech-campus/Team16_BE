package com.daggle.animory.domain.shortform.exception;

public class AlreadyLikedPetVideoException extends RuntimeException {
    @Override
    public String getMessage() {
        return ShortFormExceptionMessage.ALREADY_LIKED_PET_VIDEO.getMessage();
    }
}
