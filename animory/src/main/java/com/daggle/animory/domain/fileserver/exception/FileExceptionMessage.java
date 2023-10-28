package com.daggle.animory.domain.fileserver.exception;

public enum FileExceptionMessage {
    NOT_FOUND_IMAGE("해당하는 이미지 파일이 존재하지 않습니다."),
    NOT_FOUND_VIDEO("해당하는 비디오 파일이 존재하지 않습니다."),
    INVALID_IMAGE_TYPE("이미지 형식이 올바르지 않습니다."),
    INVALID_VIDEO_TYPE("비디오 형식이 올바르지 않습니다."),
    INVALID_FILE_TYPE("파일 형식이 올바르지 않습니다."),
    AMAZON_S3_SAVE_ERROR("AWS S3에 저장하는 과정에서 오류가 발생했습니다.");

    private final String message;

    FileExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
