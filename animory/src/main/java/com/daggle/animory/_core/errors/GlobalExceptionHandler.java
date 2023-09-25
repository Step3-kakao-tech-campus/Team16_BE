package com.daggle.animory._core.errors;


import com.daggle.animory._core.errors.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BadRequest400.class)
    public ResponseEntity<?> badRequest(BadRequest400 e){
        return new ResponseEntity<>(e.body(), e.status());
    }

    @ExceptionHandler(UnAuthorized401.class)
    public ResponseEntity<?> unAuthorized(UnAuthorized401 e){
        return new ResponseEntity<>(e.body(), e.status());
    }

    @ExceptionHandler(Forbidden403.class)
    public ResponseEntity<?> forbidden(Forbidden403 e){
        return new ResponseEntity<>(e.body(), e.status());
    }

    @ExceptionHandler(NotFound404.class)
    public ResponseEntity<?> notFound(NotFound404 e){
        return new ResponseEntity<>(e.body(), e.status());
    }

}
