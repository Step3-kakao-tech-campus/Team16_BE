package com.daggle.animory.common.errors.exception;

import com.daggle.animory.common.Response;
import lombok.Getter;
import org.springframework.http.HttpStatus;


// 유효성 검사 실패, 잘못된 파라메터 요청
@Getter
public class NotFound404 extends RuntimeException {

    public NotFound404(String message) {
        super(message);
    }

    public Response body(){
        return Response.error(getMessage(), HttpStatus.NOT_FOUND);
    }

    public HttpStatus status(){
        return HttpStatus.NOT_FOUND;
    }
}