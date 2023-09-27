package com.daggle.animory.common.error;


import com.daggle.animory.common.Response;
import com.daggle.animory.common.error.exception.BadRequest400;
import com.daggle.animory.common.error.exception.Forbidden403;
import com.daggle.animory.common.error.exception.NotFound404;
import com.daggle.animory.common.error.exception.UnAuthorized401;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.ServletException;


@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler({
        BadRequest400.class,
        MethodArgumentTypeMismatchException.class,
        HttpMessageNotReadableException.class,
        IllegalArgumentException.class
    })
    public Response<Void> badRequest(final RuntimeException e){
        return Response.error(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
        HttpRequestMethodNotSupportedException.class,
        MissingRequestValueException.class
    })
    public Response<Void> badRequest(final ServletException e){
        return Response.error(e.getMessage(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(UnAuthorized401.class)
    public Response<Void> unAuthorized(final UnAuthorized401 e){
        return Response.error(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Forbidden403.class)
    public Response<Void> forbidden(final Forbidden403 e){

        return Response.error(e.getMessage(), HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler({
        NotFound404.class,
        NotImplementedException.class
    })
    public Response<Void> notFound(final RuntimeException e){
        return Response.error(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public Response<Void> notFound(final ServletException e){
        return Response.error(e.getMessage(), HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(Exception.class)
    public Response<Void> internalServerError(final Exception e){
        return Response.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
