package com.daggle.animory.domain.fileserver.exeption;

public class NotFoundVideoException extends RuntimeException {
    @Override
    public String getMessage() {
        return FileExceptionMessage.NOT_FOUND_VIDEO.getMessage();
    }
}
