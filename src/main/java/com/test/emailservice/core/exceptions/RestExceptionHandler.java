package com.test.emailservice.core.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(value = {IOException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse badRequest(Exception ex) {
        return new ErrorResponse(400, "Bad Request", null);
    }

    @ExceptionHandler(value = {CustomException.class})
    public ResponseEntity validationException(CustomException ex) {
        return ResponseEntity.status(ex.getCode()).body(new ErrorResponse(ex.getCode().value(), ex.getMessage(), ex.getErrors()));
    }

    @ExceptionHandler(value = {RuntimeException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse unKnownException(Exception ex) {
        return new ErrorResponse(404, ex.getMessage(), null);
    }

}
