package com.daggle.animory.domain.fileserver.exeption;

public class NotFoundImageException extends RuntimeException {
    @Override
    public String getMessage() {
        return FileExceptionMessage.NOT_FOUND_IMAGE.getMessage();
    }
}
