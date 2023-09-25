package com.daggle.animory.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * API 응답을 위한 공통 클래스 <br>
 *
 * Controller 에서 사용하는 예제는 아래 테스트 코드를 참고해주세요. <br>
 * {@link com.daggle.animory.common.ResponseTest }
 */
@Getter
@RequiredArgsConstructor
public final class Response<T> {
    private final boolean success;
    private final T response;
    private final ErrorResponse error;

    public static <T> Response<T> success() {
        return new Response<>(true, null, null);
    }

    public static <T> Response<T> success(final T response) {
        return new Response<>(true, response, null);
    }

    public static Response<Void> error(final String message, final String status) {
        return new Response<>(false, null, new ErrorResponse(message, status));
    }


    private record ErrorResponse(String message, String status) { }
}
