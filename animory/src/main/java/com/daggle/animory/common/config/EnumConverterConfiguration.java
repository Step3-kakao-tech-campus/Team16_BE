package com.daggle.animory.common.config;

import com.daggle.animory.domain.shelter.ProvinceConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class EnumConverterConfiguration implements WebMvcConfigurer {
    @Override
    public void addFormatters(final FormatterRegistry registry) {
        registry.addConverter(new ProvinceConverter());
    }
}
