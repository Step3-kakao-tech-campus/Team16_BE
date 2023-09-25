package com.daggle.animory.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ResponseTest {
    private final ObjectMapper om = new ObjectMapper();

    @Test
    void test200() throws JsonProcessingException {
        final Response<Void> response = mockControllerMethodReturn200();

        System.out.println(om.writeValueAsString(response));

        assertAll(
            () -> assertThat(response.isSuccess()).isTrue(),
            () -> assertThat(response.getResponse()).isNull(),
            () -> assertThat(response.getError()).isNull()
        );
    }

    @Test
    void test200_withContent() throws JsonProcessingException {
        final Response<DummyObject> response = mockControllerMethodReturn200_withContent();

        System.out.println(om.writeValueAsString(response));

        assertAll(
            () -> assertThat(response.isSuccess()).isTrue(),
            () -> assertThat(response.getResponse().getName()).isEqualTo("dummy"),
            () -> assertThat(response.getError()).isNull()
        );
    }


    @Test
    void test404() throws JsonProcessingException {
        final Response<Void> response = mockControllerMethodReturn404();

        System.out.println(om.writeValueAsString(response));

        assertAll(
            () -> assertThat(response.isSuccess()).isFalse(),
            () -> assertThat(response.getResponse()).isNull(),
            () -> assertThat(response.getError()).isNotNull()
        );
    }


    // Controller Method 에서의 사용법 예제를 보여 주기 위한 코드 입니다.
    // 특히, 반환 값 타입 지정 부분

    private Response<Void> mockControllerMethodReturn200() {
        return Response.success();
    }

    private Response<DummyObject> mockControllerMethodReturn200_withContent() {
        return Response.success(new DummyObject("dummy"));
    }

    private Response<Void> mockControllerMethodReturn404() {
        return Response.error("error", HttpStatus.NOT_FOUND);
    }

    private static final class DummyObject {
        private final String name;

        public DummyObject(final String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}