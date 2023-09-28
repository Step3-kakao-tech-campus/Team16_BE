package com.daggle.animory.common.errors.exception;

import com.daggle.animory.common.Response;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public class UnAuthorized401 extends RuntimeException {

    public UnAuthorized401(String message) {
        super(message);
    }

    public Response body(){
        return Response.error(getMessage(), HttpStatus.UNAUTHORIZED);
    }

    public HttpStatus status(){
        return HttpStatus.UNAUTHORIZED;
    }
}