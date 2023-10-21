package com.daggle.animory.common.config;

import com.daggle.animory.common.logger.RequestLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class InterceptorConfiguration implements WebMvcConfigurer {

    private final RequestLogger requestLogger;

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {

        // Request Logger 를 모든 요청에 대해 적용
        registry.addInterceptor(requestLogger);
    }
}
