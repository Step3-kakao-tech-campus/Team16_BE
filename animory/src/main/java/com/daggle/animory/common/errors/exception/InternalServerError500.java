package com.daggle.animory.common.errors.exception;


import com.daggle.animory.common.Response;
import lombok.Getter;
import org.springframework.http.HttpStatus;


// 유효성 검사 실패, 잘못된 파라메터 요청
@Getter
public class InternalServerError500 extends RuntimeException {

    public InternalServerError500(String message) {
        super(message);
    }

    public Response body(){
        return
                Response.error(getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public HttpStatus status(){
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}