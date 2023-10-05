package com.daggle.animory.common.security;

import com.daggle.animory.domain.account.entity.AccountRole;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenProvider {

    @Value("${jwt.secret}")
    private String key;

    @Value("${jwt.token-validity-in-seconds}")
    private long tokenValiditySeconds;

    private final UserDetailsServiceImpl userDetailsService;
    public static final String TOKEN_PREFIX = "Bearer ";
    private static final String ROLES_CLAIM = "roles";
    private static final String AUTHORIZATION_HEADER = "Authorization";


    // secretKey를 Base64로 인코딩
    @PostConstruct
    public void init() throws Exception {
        this.key = Base64.getEncoder().encodeToString(key.getBytes());
    }


    public String create(String account, AccountRole role) {
        Date now = new Date(); // TODO: Date가 아닌 다른 방법을 찾아보라네요? (java.util... 대신 java.time ... 으로)
        Date expiration = new Date(now.getTime() + tokenValiditySeconds * 1000);
        log.debug("expiration : " + expiration);
        return TOKEN_PREFIX + Jwts.builder()
                .setSubject(account) // 정보 저장
                .claim(ROLES_CLAIM, role)
                .setIssuedAt(new Date()) // 토큰 발행 시간
                .setExpiration(expiration) // 토큰 만료 시간
                .signWith(SignatureAlgorithm.HS256, key)  // 암호화 알고리즘 및 secretKey
                .compact();
    }

    // 인증 정보 조회
    public Authentication getAuthentication(String token) {
        String accountSubject = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject();
        UserDetailsImpl userDetails = userDetailsService.loadUserByUsername(accountSubject);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 헤더에서 token 추출
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // 토큰의 유효 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(key).parseClaimsJws(jwtToken);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        } catch (NullPointerException ex){
            log.error("JWT RefreshToken is empty");
        }
        return false;
    }
}