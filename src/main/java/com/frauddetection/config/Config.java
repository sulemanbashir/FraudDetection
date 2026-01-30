package com.frauddetection.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Config implements WebMvcConfigurer {
    @Autowired
    RequestListener requestListener;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestListener);
    }
}
