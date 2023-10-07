package com.daggle.animory.common.security;

import com.daggle.animory.common.error.exception.UnAuthorized401;
import com.daggle.animory.domain.account.entity.AccountRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
public class TokenProvider {

    private final String key;
    private final long tokenValiditySeconds;
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String ROLES_CLAIM = "roles";

    public TokenProvider(@Value("${jwt.secret}") final String key,
                         @Value("${jwt.token-validity-in-seconds}") final long tokenValiditySeconds) {
        this.key = Base64.getEncoder().encodeToString(key.getBytes());
        this.tokenValiditySeconds = tokenValiditySeconds;
    }


    public String create(final String email, final AccountRole role) {
        final Date now = new Date();
        final Date expiration = new Date(now.getTime() + tokenValiditySeconds * 1000);

        log.debug("expiration : " + expiration);

        return TOKEN_PREFIX + Jwts.builder().setSubject(email) // 정보 저장
            .claim(ROLES_CLAIM, role).setIssuedAt(new Date()) // 토큰 발행 시간
            .setExpiration(expiration) // 토큰 만료 시간
            .signWith(SignatureAlgorithm.HS256, key)  // 암호화 알고리즘 및 secretKey
            .compact();
    }


    // 토큰 검증, 인증 정보 조회
    public String getEmailFromToken(final Claims claims) {
        return claims.getSubject();
    }

    public AccountRole getRoleFromToken(final Claims claims) {
        return AccountRole.valueOf(claims.get(ROLES_CLAIM).toString());
    }

    // 헤더에서 token 추출
    public Claims resolveToken(final String bearerToken) {
        if (!StringUtils.hasText(bearerToken) || !bearerToken.startsWith(TOKEN_PREFIX))
            throw new UnAuthorized401("토큰 형식이 올바르지 않습니다.");

        try {
            final String token = cutTokenPrefix(bearerToken);

            return extractBody(token);
        } catch (final Exception ex) {
            log.debug("Jwt validation error");
            throw new UnAuthorized401("토큰이 유효하지 않습니다.");
        }
    }

    private String cutTokenPrefix(final String bearerToken) {
        return bearerToken.substring(7);
    }

    private Claims extractBody(final String token) {
        return Jwts.parser()
            .setSigningKey(key)
            .parseClaimsJws(token)
            .getBody();
    }


}