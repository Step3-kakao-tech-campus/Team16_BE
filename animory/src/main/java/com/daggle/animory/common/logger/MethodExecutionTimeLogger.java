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
     * 모든 method의 실행시간을 로깅합니다.
     */
    @Around("execution(* com.daggle.animory..*(..))")
    public Object logExecutionTime(final ProceedingJoinPoint joinPoint) throws Throwable {
        final long startTime = System.currentTimeMillis();
        final Object result = joinPoint.proceed();
        final long endTime = System.currentTimeMillis();

        final long executionTimeMillis = endTime - startTime;

        final String className = joinPoint.getSignature()
            .getDeclaringType()
            .getSimpleName();
        final String methodName = joinPoint.getSignature()
            .getName();

        log.info("{}.{} took {}ms", className, methodName, executionTimeMillis);

        return result;
    }
}
