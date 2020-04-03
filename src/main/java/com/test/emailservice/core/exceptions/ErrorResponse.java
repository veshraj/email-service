package com.test.emailservice.core.exceptions;

import java.util.Map;

public class ErrorResponse {
    private int code;
    private String message;
    private Map<String, Object> errors;



    public ErrorResponse()
    {
        super();
    }
    public ErrorResponse(int status, String message, Map<String, Object> errors)
    {
        super();
        this.code = status;
        this.message = message;
        this.errors = errors;
    }

    public int getCode()
    {
        return code;
    }
    public void setCode(int code)
    {
        this.code = code;
    }
    public String getMessage()
    {
        return message;
    }
    public void setMessage(String message)
    {
        this.message = message;
    }
    @Override
    public String toString()
    {
        return "ErrorResponse [code=" + code + ", message=" + message + "]";
    }

    public Map<String, Object> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, Object> errors) {
        this.errors = errors;
    }
}
