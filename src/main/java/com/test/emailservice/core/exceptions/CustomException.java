package com.test.emailservice.core.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;

public class CustomException extends RuntimeException {
    private HttpStatus code;
    private String message;
    private Map<String, Object> errors;

    public CustomException(HttpStatus code, String message, HashMap errors) {
        this.code = code;
        this.message = message;
        this.errors = errors;
    }

    public String getMessage() {
        return this.message;
    }

    public HttpStatus getCode() {
        return code;
    }

    public Map<String, Object> getErrors() {
        return errors;
    }
}
