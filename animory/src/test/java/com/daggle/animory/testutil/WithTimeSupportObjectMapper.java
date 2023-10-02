package com.daggle.animory.testutil;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * time type serialization 설정이 추가된 object mapper 가 지원됩니다.
 * DataJpaTest 등에서 결과를 콘솔에 찍어보고자 할때 상속해서 사용하면 좋을 듯 합니다.
 */
public class WithTimeSupportObjectMapper {

    protected ObjectMapper om = new ObjectMapper()
        .registerModule(new JavaTimeModule());

    protected void print(final Object o) {
        try{
            System.out.println(om.writerWithDefaultPrettyPrinter().writeValueAsString(o));
        } catch (final JsonProcessingException e){
            System.out.println("json parsing error: " + e.getMessage());
        }
    }

}
