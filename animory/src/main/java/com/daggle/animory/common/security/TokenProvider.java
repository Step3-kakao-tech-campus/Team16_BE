package com.daggle.animory.common.security;

import com.daggle.animory.domain.account.entity.AccountRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
public class TokenProvider {

    private final String key;
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String ROLES_CLAIM = "roles";

    private static final int TOKEN_EXPIRATION_DATE_TO_PLUS = 1;
    private static final int TOKEN_EXPIRATION_FIXED_HOUR = 3;

    public TokenProvider(@Value("${jwt.secret}") final String key) {
        this.key = Base64.getEncoder().encodeToString(key.getBytes());
    }


    public String create(final String email, final AccountRole role) {
        return TOKEN_PREFIX + Jwts.builder().setSubject(email) // 정보 저장
                .claim(ROLES_CLAIM, role).setIssuedAt(new Date()) // 토큰 발행 시간
                .setExpiration(calcExpirationDateTime()) // 토큰 만료 시간
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
        final String token = cutTokenPrefix(bearerToken);
        return extractBody(token);
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

    private Date calcExpirationDateTime() {
        final LocalDateTime currentTime = LocalDateTime.now(); // 현재 시각으로 부터

        final LocalDateTime expirationDateTime = currentTime
                .plusDays(TOKEN_EXPIRATION_DATE_TO_PLUS) // day를 더하고
                .withHour(TOKEN_EXPIRATION_FIXED_HOUR); // 고정된 시각

        return convertLocalDateTimeToDate(expirationDateTime);
    }

    private Date convertLocalDateTimeToDate(final LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(java.time.ZoneId.systemDefault()).toInstant());
    }


}