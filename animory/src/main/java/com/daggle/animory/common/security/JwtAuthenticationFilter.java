package com.daggle.animory.common.security;

import com.daggle.animory.domain.account.entity.Account;
import com.daggle.animory.domain.account.entity.AccountRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
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

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, TokenProvider tokenProvider) {
        super(authenticationManager);
        this.tokenProvider = tokenProvider;
    }

    // 권한 확인을 수행하는 로직
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String jwt = request.getHeader(AUTHORIZATION_HEADER);

        if (jwt == null) {
            super.doFilterInternal(request, response, chain);
            return;
        }

        try {
            // jwt 유효하다면 -> authority에 대한 claim 추출 -> authentication 객체 생성 -> security context에 넣어줌
            final Claims claims = tokenProvider.resolveToken(jwt);
            final String email = tokenProvider.getEmailFromToken(claims);
            final AccountRole role = tokenProvider.getRoleFromToken(claims);

            Account account = Account.builder()
                    .email(email)
                    .role(role)
                    .build();
            UserDetailsImpl userDetails = new UserDetailsImpl(account);

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    userDetails.getPassword(),
                    userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);

            log.debug("디버그 : 인증 객체 만들어짐");
        } catch (SignatureException sve) {
            log.warn("토큰 검증 실패");
        } catch (ExpiredJwtException tee) {
            log.warn("토큰 만료됨");
        } finally {
            super.doFilterInternal(request, response, chain);
        }
    }
}
