package com.daggle.animory.common.logger;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class MethodExecutionTimeLogger {

    /**
     * method의 실행시간을 로깅합니다.
     *
     * 다음 클래스와 패키지를 제외합니다.
     * - com.daggle.animory.common.logger.RequestLogger
     * - com.daggle.animory.common.error
     */
    @Around("execution(* com.daggle.animory..*(..)) && " +
        "!execution(* com.daggle.animory.common.logger.RequestLogger.*(..)) && " +
        "!execution(* com.daggle.animory.common.exception.*.*(..))")
    public Object logExecutionTime(final ProceedingJoinPoint joinPoint) throws Throwable {
        if(!log.isInfoEnabled()) return joinPoint.proceed(); // Logger Level 적용

        // 메소드 실행시간 측정
        final long startTime = System.currentTimeMillis();
        final Object result = joinPoint.proceed();
        final long endTime = System.currentTimeMillis();

        final long executionTimeMillis = endTime - startTime;

        // 클래스명, 메소드명 읽기
        final String className = joinPoint.getSignature()
            .getDeclaringType()
            .getSimpleName();
        final String methodName = joinPoint.getSignature()
            .getName();
        
        log.info("{}.{} took {}ms", className, methodName, executionTimeMillis);

        return result;
    }
}
