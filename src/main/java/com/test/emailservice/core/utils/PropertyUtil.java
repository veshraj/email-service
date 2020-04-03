package com.test.emailservice.core.utils;

import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class PropertyUtil implements EnvironmentAware {
    /**
     * The system environment.
     */
    public static Environment environment;

    public static Object getConfigProperty(String key) {
        return environment.getProperty(key);
    }

    @Override
    public void setEnvironment(Environment arg0) {
        if (environment == null) {
            environment = arg0;
        }

    }
}
