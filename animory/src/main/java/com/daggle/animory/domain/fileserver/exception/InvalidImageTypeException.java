package com.daggle.animory.domain.fileserver.exception;

public class InvalidImageTypeException extends RuntimeException {
    @Override
    public String getMessage() {
        return FileExceptionMessage.INVALID_IMAGE_TYPE.getMessage();
    }
}
