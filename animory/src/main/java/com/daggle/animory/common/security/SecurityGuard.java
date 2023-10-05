package com.daggle.animory.common.security;

import com.daggle.animory.common.error.exception.InternalServerError500;
import com.daggle.animory.common.error.exception.UnAuthorized401;
import com.daggle.animory.domain.account.AccountRepository;
import com.daggle.animory.domain.account.entity.Account;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;



/**
 * @Auth 어노테이션이 붙은 컨트롤러의 메소드를 검사합니다.
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

    @Around("execution(* *(.., @Auth (*), ..))")
    public Object validateAuthorization(final ProceedingJoinPoint joinPoint) throws Throwable {
        try{
            final String token = tokenProvider.resolveToken(getAuthorizationHeaderFromRequest());

            final String email = tokenProvider.getEmailFromToken(token);

            final Account account = findAccountByEmail(email);

            return joinPoint.proceed(
                injectAccountToController(joinPoint, account)
            );

        } catch (final Exception e) {
            log.warn("Exception : " + e.getMessage());
            throw new InternalServerError500("사용자 인증 과정 중 알 수 없는 오류가 발생했습니다.");
        }
    }

    private static Object[] injectAccountToController(final ProceedingJoinPoint joinPoint, final Account account) {
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
