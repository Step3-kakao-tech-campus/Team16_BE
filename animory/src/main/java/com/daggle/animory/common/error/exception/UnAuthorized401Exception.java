package com.daggle.animory.common.error.exception;


import com.daggle.animory.common.Response;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public class UnAuthorized401Exception extends RuntimeException {

    public UnAuthorized401Exception(final String message) {
        super(message);
    }

    public Response<Void> body(){
        return Response.error(getMessage(), HttpStatus.UNAUTHORIZED);
    }

    public HttpStatus status(){
        return HttpStatus.UNAUTHORIZED;
    }
}