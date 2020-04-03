package com.test.emailservice.modules.auth.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.http.HttpStatus;

@JsonIgnoreProperties(ignoreUnknown = true,value = {"stackTrace", "cause", "suppressed"})
public class CustomException extends RuntimeException {
    private String message;
    private int code;
    private HttpStatus statusCode;

    protected CustomException() {
    }

    public CustomException(
            String message, HttpStatus statusCode) {
        this.message = message;
        this.code = statusCode.value();
        this.statusCode = statusCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }
}

