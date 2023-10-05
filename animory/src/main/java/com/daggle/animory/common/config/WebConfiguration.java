package com.daggle.animory.common.config;

import com.daggle.animory.common.logger.RequestLogger;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {


    @Override
    public void addInterceptors(final InterceptorRegistry registry) {

        // Request Logger 를 모든 요청에 대해 적용
        registry.addInterceptor(new RequestLogger()).order(-1);
    }

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
