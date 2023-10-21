package com.daggle.animory.testutil.webmvctest;

import com.daggle.animory.common.security.TokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

/**
 * 이 테스트는 SecurityGuard(AOP)를 비활성화 합니다.
 *
 * TokenProvider import를 지우면 테스트가 터지는 이유를 모르겠음.
 */
@WebMvcTest({TokenProvider.class})
public abstract class BaseWebMvcTest {
    @Autowired
    protected MockMvc mvc;
    protected ObjectMapper om = new ObjectMapper()
        .registerModule(new JavaTimeModule());

}
