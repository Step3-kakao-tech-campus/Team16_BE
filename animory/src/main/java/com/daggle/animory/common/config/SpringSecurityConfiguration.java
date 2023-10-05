package com.daggle.animory.common.config;

import com.daggle.animory.common.error.exception.Forbidden403;
import com.daggle.animory.common.error.exception.UnAuthorized401;
import com.daggle.animory.common.security.TokenFilter;
import com.daggle.animory.common.security.TokenProvider;
import com.daggle.animory.common.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Configuration
@EnableWebSecurity // 시큐리티 필터(SpringSecurityConfiguration)가 필터체인에 등록
@RequiredArgsConstructor
public class SpringSecurityConfiguration {
    private final TokenProvider tokenProvider;
    private final UserDetailsServiceImpl userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http,
                                           @Autowired @Qualifier("handlerExceptionResolver")
                                           final HandlerExceptionResolver resolver) throws Exception {
        http.csrf().disable() // csrf 해제
                .formLogin().disable()// form 로그인 비활성화
                .cors().configurationSource(configurationSource()) // cors 재설정

                // jwt 사용
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                // 커스텀 필터 적용
                .and()
                .addFilterBefore(new TokenFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)

                // 인증 실패 처리
                .exceptionHandling().authenticationEntryPoint((request, response, authException) ->
                    resolver.resolveException(request, response, null, new UnAuthorized401("인증되지 않았습니다")))

                // 권한 실패 처리
                .and()
                .exceptionHandling().accessDeniedHandler((request, response, accessDeniedException) ->
                    resolver.resolveException(request, response, null, new Forbidden403("권한이 없습니다")))

                // Endpoint 인증/인가 필터 설정
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/shelter/**", "/pet/**").access("hasRole('ROLE_SHELTER')")
                .antMatchers(HttpMethod.PATCH, "/pet/**").access("hasRole('ROLE_SHELTER')")
                .anyRequest().permitAll();

        return http.build();
    }

    public CorsConfigurationSource configurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedOriginPattern("*"); // 모든 IP 주소 허용
        configuration.setAllowCredentials(true); // 클라이언트에서 쿠키 요청 허용
        configuration.addExposedHeader("Authorization");

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }
}
