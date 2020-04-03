package com.test.emailservice.core.utils;

import org.springframework.validation.BindingResult;

import java.util.HashMap;

public class BindingErrorUtil {
    public static HashMap<String, Object> getBindingErrors(BindingResult errorBag) {
        HashMap<String, Object> errors = new HashMap<>();
        try {
            HashMap<String, Object> msgbody = new HashMap<>();
            errorBag.getFieldErrors().forEach(f -> errors.put(f.getField(), f.getDefaultMessage()));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return errors;
    }
}
