package com.daggle.animory.common.security;

import com.daggle.animory.domain.account.entity.Account;
import com.daggle.animory.domain.account.entity.AccountRole;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 권한이 필요한 요청에 대해 해당 유저의 jwt로 권한을 확인하는 필터
@Slf4j
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private final TokenProvider tokenProvider;

    public JwtAuthenticationFilter(final AuthenticationManager authenticationManager, final TokenProvider tokenProvider) {
        super(authenticationManager);
        this.tokenProvider = tokenProvider;
    }

    // 권한 확인을 수행하는 로직
    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain) throws IOException, ServletException {
        final String jwt = request.getHeader(AUTHORIZATION_HEADER);

        if (jwt == null) {
            super.doFilterInternal(request, response, chain);
            return;
        }

        try {
            // jwt 유효하다면 -> authority에 대한 claim 추출 -> authentication 객체 생성 -> security context에 넣어줌
            final Claims claims = tokenProvider.resolveToken(jwt);
            final String email = tokenProvider.getEmailFromToken(claims);
            final AccountRole role = tokenProvider.getRoleFromToken(claims);

            final Account account = Account.builder()
                    .email(email)
                    .role(role)
                    .build();
            final UserDetailsImpl userDetails = new UserDetailsImpl(account);

            final Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    userDetails.getPassword(),
                    userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);

            log.debug("디버그 : 인증 객체 만들어짐");
        } catch (SecurityException e) {
            log.info("Invalid JWT signature.");
            throw new JwtException("잘못된 JWT 시그니처");
        } catch (MalformedJwtException e) {
            log.info("Invalid JWT token.");
            throw new JwtException("유효하지 않은 JWT 토큰");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
            throw new JwtException("토큰 기한 만료");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
            throw new JwtException("JWT token compact of handler are invalid.");
        } finally {
            super.doFilterInternal(request, response, chain);
        }
    }
}
