package com.daggle.animory.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedHeaders("*")
            .allowedMethods("*")
            .allowedOrigins("*")
            .exposedHeaders("Authorization")
            .allowCredentials(false);
    }
}
