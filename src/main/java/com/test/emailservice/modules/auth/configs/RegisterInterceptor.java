package com.test.emailservice.modules.auth.configs;

import com.test.emailservice.modules.auth.interceptors.AuthUserInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class RegisterInterceptor implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthUserInterceptor());
    }
}
