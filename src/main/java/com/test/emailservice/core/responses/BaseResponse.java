package com.test.emailservice.core.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@JsonIgnoreProperties({"httpHeaders", "response"})
public class BaseResponse<T> {
    public HttpStatus code;
    public T data;
    public String message;
    public HttpHeaders httpHeaders;

    public BaseResponse(String message, HttpStatus httpStatus, T data) {
        this.code = httpStatus;
        this.data = data;
        this.message = message;
    }

    public ResponseEntity<BaseResponse<T>> getResponse() {
        return ResponseEntity.status(code).headers(httpHeaders).body( this);
    }

    public BaseResponse setHeaders(HttpHeaders headers) {
        this.httpHeaders = headers;
        return this;
    }
}
