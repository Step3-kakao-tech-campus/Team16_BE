package com.daggle.animory.domain.shelter;


import com.daggle.animory.domain.shelter.entity.Province;
import org.springframework.core.convert.converter.Converter;

public class ProvinceConverter implements Converter<String, Province> {

    @Override
    public Province convert(final String source) {
        return Province.fromJson(source);
    }
}
