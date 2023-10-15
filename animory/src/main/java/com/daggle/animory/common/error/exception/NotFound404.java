package com.daggle.animory.common.error.exception;


import com.daggle.animory.common.Response;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public class NotFound404 extends RuntimeException {

    public NotFound404(final String message) {
        super(message);
    }

    public Response<Void> body(){
        return Response.error(getMessage(), HttpStatus.NOT_FOUND);
    }

    public HttpStatus status(){
        return HttpStatus.NOT_FOUND;
    }
}