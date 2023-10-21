package com.daggle.animory.domain.fileserver.exception;

public class InvalidFileTypeException extends RuntimeException {
    @Override
    public String getMessage() {
        return FileExceptionMessage.INVALID_FILE_TYPE.getMessage();
    }
}