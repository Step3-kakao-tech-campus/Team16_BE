package com.daggle.animory.common.security;

import com.daggle.animory.common.error.exception.Forbidden403;
import com.daggle.animory.common.error.exception.UnAuthorized401;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.HandlerExceptionResolver;
@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final TokenProvider tokenProvider;

    // Custom SecurityFilterManagerImpl 클래스를 통해 JWT 필터를 추가
    public class SecurityFilterManagerImpl extends AbstractHttpConfigurer<SecurityFilterManagerImpl, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);
            builder.addFilter(new JwtAuthenticationFilter(authenticationManager, tokenProvider));
            super.configure(builder);
        }
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   @Autowired @Qualifier("handlerExceptionResolver")
                                                   final HandlerExceptionResolver resolver) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.formLogin().disable();
        http.httpBasic().disable();

        // 커스텀 필터 적용 (시큐리티 필터 교환)
        http.apply(new SecurityFilterManagerImpl());

        http.exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
            log.info(authException.getMessage());
            resolver.resolveException(request, response, null, new UnAuthorized401("인증되지 않은 사용자입니다."));
        });

        http.exceptionHandling().accessDeniedHandler((request, response, accessDeniedException) -> {
            log.info(accessDeniedException.getMessage());
            resolver.resolveException(request, response, null, new Forbidden403("권한이 없습니다"));
        });

        http.authorizeRequests(
                authorize -> authorize
                        .antMatchers(HttpMethod.POST, "/pet/**").hasAuthority("SHELTER")
                        .antMatchers(HttpMethod.PATCH).hasAuthority("SHELTER")
                        .antMatchers(HttpMethod.PUT).hasAuthority("SHELTER")
                        .anyRequest().permitAll()
        );

        return http.build();
    }
}