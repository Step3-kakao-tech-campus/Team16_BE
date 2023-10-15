package com.daggle.animory.common.logger;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * 서로 다른 스레드에 대한 요청을 구분하기 위한 ID를 생성합니다.(MDC, correlationId) <br>
 * HTTP Request의 Method, URI, Query Parameter를 로깅합니다.*
 */
@Slf4j
public class RequestLogger implements HandlerInterceptor {

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             @NonNull final HttpServletResponse response,
                             @NonNull final Object handler) {

        // 서로 다른 스레드에 대한 요청을 구분하기 위한 ID를 생성합니다.
        MDC.put("correlationId", UUID.randomUUID()
            .toString()
            .substring(0, 8));

        log.info("{} {}{}",
            request.getMethod(),
            request.getRequestURI(),
            getQueryParameters(request));

        return true;
    }

    // HTTP Request의 Query Parameter를 추출합니다.
    private String getQueryParameters(final HttpServletRequest request) {
        final String queryString = request.getQueryString();

        if (queryString == null) return StringUtils.EMPTY;
        return "?" + queryString;
    }
}
