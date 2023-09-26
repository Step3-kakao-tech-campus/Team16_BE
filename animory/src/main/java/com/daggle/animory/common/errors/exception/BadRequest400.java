package com.daggle.animory.common.errors.exception;

import com.daggle.animory.common.Response;
import lombok.Getter;
import org.springframework.http.HttpStatus;


// 유효성 검사 실패, 잘못된 파라메터 요청
@Getter
public class BadRequest400 extends RuntimeException {

    public BadRequest400(String message) {
        super(message);
    }

    public Response body(){
        return Response.error(getMessage(), HttpStatus.BAD_REQUEST);
    }

    public HttpStatus status(){
        return HttpStatus.BAD_REQUEST;
    }
}