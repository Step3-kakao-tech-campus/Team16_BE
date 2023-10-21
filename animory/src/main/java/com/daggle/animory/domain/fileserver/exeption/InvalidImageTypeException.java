package com.daggle.animory.domain.fileserver.exeption;

public class InvalidImageTypeException extends RuntimeException {
    @Override
    public String getMessage() {
        return FileExceptionMessage.INVALID_IMAGE_TYPE.getMessage();
    }
}
