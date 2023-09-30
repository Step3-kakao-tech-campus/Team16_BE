package com.daggle.animory.common.error.exception;


import com.daggle.animory.common.Response;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public class InternalServerError500 extends RuntimeException {

    public InternalServerError500(String message) {
        super(message);
    }

    public Response body() {
        return
            Response.error(getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public HttpStatus status() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}