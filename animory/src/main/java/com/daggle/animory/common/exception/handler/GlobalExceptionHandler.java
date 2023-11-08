package com.daggle.animory.common.exception.handler;


import com.daggle.animory.common.Response;
import com.daggle.animory.common.security.exception.ForbiddenException;
import com.daggle.animory.common.security.exception.UnAuthorizedException;
import com.daggle.animory.domain.account.exception.AlreadyExistEmailException;
import com.daggle.animory.domain.account.exception.CheckEmailOrPasswordException;
import com.daggle.animory.domain.fileserver.exception.*;
import com.daggle.animory.domain.pet.exception.InvalidPetAgeFormatException;
import com.daggle.animory.domain.pet.exception.InvalidPetMonthRangeException;
import com.daggle.animory.domain.pet.exception.InvalidPetYearRangeException;
import com.daggle.animory.domain.pet.exception.PetNotFoundException;
import com.daggle.animory.domain.shelter.exception.ShelterAlreadyExistException;
import com.daggle.animory.domain.shelter.exception.ShelterNotFoundException;
import com.daggle.animory.domain.shelter.exception.ShelterPermissionDeniedException;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {

    // BAD REQUEST 400
    @ExceptionHandler({AlreadyExistEmailException.class})
    public ResponseEntity<Response<Void>> handleAlreadyExistEmailException(final Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.error(e.getMessage(),
                                                                                 HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler({CheckEmailOrPasswordException.class})
    public ResponseEntity<Response<Void>> handleCheckEmailOrPasswordException(final Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.error(e.getMessage(),
                                                                                 HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler({InvalidFileTypeException.class})
    public ResponseEntity<Response<Void>> handleInvalidFileTypeException(final Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.error(e.getMessage(),
                                                                                 HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler({InvalidImageTypeException.class})
    public ResponseEntity<Response<Void>> handleInvalidImageTypeException(final Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.error(e.getMessage(),
                                                                                 HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler({InvalidVideoTypeException.class})
    public ResponseEntity<Response<Void>> handleInvalidVideoTypeException(final Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.error(e.getMessage(),
                                                                                 HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler({NotFoundImageException.class})
    public ResponseEntity<Response<Void>> handleNotFoundImageException(final Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.error(e.getMessage(),
                                                                                 HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler({NotFoundVideoException.class})
    public ResponseEntity<Response<Void>> handleNotFoundVideoException(final Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.error(e.getMessage(),
                                                                                 HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler({InvalidPetAgeFormatException.class})
    public ResponseEntity<Response<Void>> handleInvalidPetAgeTypeException(final Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.error(e.getMessage(),
                                                                                 HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler({InvalidPetYearRangeException.class})
    public ResponseEntity<Response<Void>> handleInvalidPetYearRangeException(final Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.error(e.getMessage(),
                                                                                 HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler({InvalidPetMonthRangeException.class})
    public ResponseEntity<Response<Void>> handleInvalidPetMonthRangeException(final Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.error(e.getMessage(),
                                                                                 HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler({ShelterAlreadyExistException.class})
    public ResponseEntity<Response<Void>> handleShelterAlreadyExistException(final Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.error(e.getMessage(),
                                                                                 HttpStatus.BAD_REQUEST));
    }

    // 401
    @ExceptionHandler({UnAuthorizedException.class})
    public ResponseEntity<Response<Void>> handleUnAuthorizedException(final Exception e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Response.error(e.getMessage(),
                                                                                  HttpStatus.UNAUTHORIZED));
    }


    // 403
    @ExceptionHandler({ShelterPermissionDeniedException.class})
    public ResponseEntity<Response<Void>> handleShelterPermissionDeniedException(final Exception e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Response.error(e.getMessage(), HttpStatus.FORBIDDEN));
    }

    @ExceptionHandler({ForbiddenException.class})
    public ResponseEntity<Response<Void>> handleForbiddenExceptionn(final Exception e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Response.error(e.getMessage(), HttpStatus.FORBIDDEN));
    }

    // 404
    @ExceptionHandler({PetNotFoundException.class})
    public ResponseEntity<Response<Void>> handlePetNotFoundException(final Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Response.error(e.getMessage(), HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler({ShelterNotFoundException.class})
    public ResponseEntity<Response<Void>> handleShelterNotFoundException(final Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Response.error(e.getMessage(), HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler({MaxUploadSizeExceededException.class})
    public ResponseEntity<Response<Void>> handleMaxUploadSizeExceededException(final RuntimeException e) {
        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body(Response.error(e.getMessage(),
                                                                                       HttpStatus.PAYLOAD_TOO_LARGE));
    }

    // INTERNAL SERVER ERROR 500
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ApiResponse(responseCode = "500", description = "예상하지 못한 서버 오류", content = @Content)
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Response<Void>> internalServerError(final Exception e) {
        log.error("Internal Server Error", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Response.error(e.getMessage(),
                                                                                           HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler({AmazonS3SaveError.class})
    public ResponseEntity<Response<Void>> handleAmazonS3SaveException(final Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Response.error(e.getMessage(),
                                                                                           HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler({
        MethodArgumentTypeMismatchException.class,
        HttpMessageNotReadableException.class,
        IllegalArgumentException.class,

        ConstraintViolationException.class
    })
    public ResponseEntity<Response<Void>> badRequest(final RuntimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.error(e.getMessage(),
                                                                                 HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Response<Void>> handleMethodArgumentNotValid(final MethodArgumentNotValidException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.error(e.getBindingResult().getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler({
        HttpRequestMethodNotSupportedException.class,
        MissingRequestValueException.class
    })
    public ResponseEntity<Response<Void>> badRequest(final ServletException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.error(e.getMessage(),
                                                                                 HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<Response<Void>> badRequest(final BindException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.error(e.getBindingResult().getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(NotImplementedException.class)
    public ResponseEntity<Response<Void>> notFound(final RuntimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Response.error(e.getMessage(), HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Response<Void>> notFound(final ServletException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Response.error(e.getMessage(), HttpStatus.NOT_FOUND));
    }

}
