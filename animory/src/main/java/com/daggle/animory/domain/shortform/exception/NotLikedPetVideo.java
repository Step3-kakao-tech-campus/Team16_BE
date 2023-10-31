package com.daggle.animory.domain.shortform.exception;

public class NotLikedPetVideo extends RuntimeException {
    @Override
    public String getMessage() {
        return ShortFormExceptionMessage.NOT_LIKED_PET_VIDEO.getMessage();
    }
}
