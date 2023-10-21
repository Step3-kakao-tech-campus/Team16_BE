package com.daggle.animory.common.error;


import com.daggle.animory.common.Response;
import com.daggle.animory.common.error.exception.BadRequest400Exception;
import com.daggle.animory.common.error.exception.Forbidden403Exception;
import com.daggle.animory.common.error.exception.NotFound404Exception;
import com.daggle.animory.common.error.exception.UnAuthorized401Exception;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.ServletException;
import javax.validation.ConstraintViolationException;


@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {

    // BAD REQUEST 400
    @ExceptionHandler({
        BadRequest400Exception.class,
        MethodArgumentTypeMismatchException.class,
        HttpMessageNotReadableException.class,
        IllegalArgumentException.class,

        ConstraintViolationException.class
    })
    public ResponseEntity<Response<Void>> badRequest(final RuntimeException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.error(e.getMessage(), HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Response<Void>> handleMethodArgumentNotValid(final MethodArgumentNotValidException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.error(e.getBindingResult().getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler({
        HttpRequestMethodNotSupportedException.class,
        MissingRequestValueException.class
    })
    public ResponseEntity<Response<Void>> badRequest(final ServletException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.error(e.getMessage(), HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<Response<Void>> badRequest(final BindException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.error(e.getBindingResult().getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST));
    }

    // 401, 403

    @ExceptionHandler(UnAuthorized401Exception.class)
    public ResponseEntity<Response<Void>> unAuthorized(final UnAuthorized401Exception e){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Response.error(e.getMessage(), HttpStatus.UNAUTHORIZED));
    }

    @ExceptionHandler({
        Forbidden403Exception.class,
        MaxUploadSizeExceededException.class // File Size, Request Size 제한 초과
    })
    public ResponseEntity<Response<Void>> forbidden(final RuntimeException e){

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Response.error(e.getMessage(), HttpStatus.FORBIDDEN));
    }


    // NOT FOUND 404
    @ExceptionHandler({
        NotFound404Exception.class,
        NotImplementedException.class
    })
    public ResponseEntity<Response<Void>> notFound(final RuntimeException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Response.error(e.getMessage(), HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Response<Void>> notFound(final ServletException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Response.error(e.getMessage(), HttpStatus.NOT_FOUND));
    }


    // INTERNAL SERVER ERROR 500
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ApiResponse(responseCode = "500", description = "예상하지 못한 서버 오류", content = @Content)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<Void>> internalServerError(final Exception e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Response.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

}
