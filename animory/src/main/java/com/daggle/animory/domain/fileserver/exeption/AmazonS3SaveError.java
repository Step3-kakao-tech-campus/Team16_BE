package com.daggle.animory.domain.fileserver.exeption;

public class AmazonS3SaveError extends RuntimeException {
    String message;
    public AmazonS3SaveError(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return FileExceptionMessage.AMAZON_S3_SAVE_ERROR.getMessage() + message;
    }
}