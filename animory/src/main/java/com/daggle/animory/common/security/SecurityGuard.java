package com.daggle.animory.common.security;

import com.daggle.animory.common.error.exception.Forbidden403;
import com.daggle.animory.common.error.exception.UnAuthorized401;
import com.daggle.animory.domain.account.AccountRepository;
import com.daggle.animory.domain.account.entity.Account;
import com.daggle.animory.domain.account.entity.AccountRole;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;



/**
 * Authorized 어노테이션이 붙은 컨트롤러 혹은 컨트롤러의 메소드를 검사합니다.
 * Http Request Header 에서 Authorization field 를 가져와서 토큰을 검증합니다.
 * 토큰이 유효하면, Account 객체를 메소드 인자로 주입합니다.
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class SecurityGuard {
    private final TokenProvider tokenProvider;
    private final AccountRepository accountRepository;

    private static final String AUTHORIZATION_HEADER = "Authorization";

    // TODO: 인증 과정의 예외와, 예상치 못한 에러를 구분할 수 있어야 함.
    @Around("@within(com.daggle.animory.common.security.RequireRole) || @annotation(com.daggle.animory.common.security.RequireRole)")
    public Object validateAuthorization(final ProceedingJoinPoint joinPoint) throws Throwable {
        try{
            final AccountRole[] allowedRoles = getAllowedRoles(joinPoint);

            final Claims claims = tokenProvider.resolveToken(getAuthorizationHeaderFromRequest());

            final String email = tokenProvider.getEmailFromToken(claims);
            final AccountRole role = tokenProvider.getRoleFromToken(claims);

            validateRolePermission(allowedRoles, role);

            final Account account = findAccountByEmail(email);

            return joinPoint.proceed(
                injectAccountToController(joinPoint, account)
            );

        } catch (final Exception e) {
            log.warn("Exception : " + e.getMessage());
            throw e;
        }
    }

    private void validateRolePermission(final AccountRole[] allowedRoles, final AccountRole role) {
        if(allowedAllRoles(allowedRoles)) return;

        if (Arrays.stream(allowedRoles).noneMatch(allowedRole -> allowedRole.equals(role)))
            throw new Forbidden403("권한이 없습니다.");
    }
    private boolean allowedAllRoles(final AccountRole[] allowedRoles) {
        return allowedRoles.length == 0; // Authorized 어노테이션은 기본값으로 배열이 비어있는데, 이 경우는 모든 권한을 허용한다는 의미입니다.
    }


    /**
     * Method Level Annotation Value를 먼저 가져오려고 시도한다. <br>
     * 없다면, Class Level Annotation Value를 가져온다.
     */
    private AccountRole[] getAllowedRoles(final ProceedingJoinPoint joinPoint) {
        // 먼저 메소드 레벨 어노테이션 획득을 시도합니다.
        final MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        final RequireRole methodLevelAnnotation = methodSignature.getMethod().getAnnotation(RequireRole.class);

        if (methodLevelAnnotation != null) {
            return methodLevelAnnotation.value();
        }

        // 메소드 레벨 어노테이션이 없으면 클래스 레벨 어노테이션 획득을 시도합니다.(반드시 존재합니다.)
        final Class<?> declaringType = joinPoint.getSignature().getDeclaringType();
        final RequireRole classLevelAnnotation = declaringType.getAnnotation(RequireRole.class);

        return classLevelAnnotation.value();
    }

    private static Object[] injectAccountToController(final ProceedingJoinPoint joinPoint, final Account account) {
        // Controller Method 파라미터에 해당 타입이 존재하지 않더라도 예외로 간주하지는 않습니다.

        return Arrays.stream(joinPoint.getArgs())
            .map(arg -> (arg instanceof Account) ? account : arg)
            .toArray();
    }

    private Account findAccountByEmail(final String email) {
        return accountRepository.findByEmail(email)
            .orElseThrow(() -> new UnAuthorized401("존재하지 않는 사용자입니다."));
    }

    private String getAuthorizationHeaderFromRequest() {
        final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getHeader(AUTHORIZATION_HEADER);
    }


}
