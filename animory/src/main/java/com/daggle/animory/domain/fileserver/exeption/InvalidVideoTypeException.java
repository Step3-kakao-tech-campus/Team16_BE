package com.daggle.animory.domain.fileserver.exeption;

public class InvalidVideoTypeException extends RuntimeException {
    @Override
    public String getMessage() {
        return FileExceptionMessage.INVALID_VIDEO_TYPE.getMessage();
    }
}