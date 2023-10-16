package com.daggle.animory.common.security;


import com.daggle.animory.domain.account.entity.AccountRole;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * Annotation이 붙은 Controller의 메소드는 인증된 사용자만 접근할 수 있습니다.
 *
 * Controller  파라미터에 Account 객체를 주입합니다. (타입만 일치하면 되고, 파라미터의 순서나 개수는 상관없습니다.)
 * Account 타입의 파라미터가 존재하지 않더라도 정상적으로 동작합니다.(인증은 요구 하되 정보는 필요없는 경우)
 *
 * Class Level, Method Level 모두 붙일 수 있으며, 우선순위는 Method Level이 높습니다.
 * (@Transactional과 동일한 동작)
 * </pre>
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequireRole {

    @AliasFor("roles")
    AccountRole[] value() default {};

    @AliasFor("value")
    AccountRole[] roles() default {};
}