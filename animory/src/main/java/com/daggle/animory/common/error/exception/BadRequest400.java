package com.daggle.animory.common.error.exception;

import com.daggle.animory.common.Response;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public class BadRequest400 extends RuntimeException {

    public BadRequest400(final String message) {
        super(message);
    }

    public Response<Void> body(){
        return Response.error(getMessage(), HttpStatus.BAD_REQUEST);
    }

    public HttpStatus status(){
        return HttpStatus.BAD_REQUEST;
    }
}