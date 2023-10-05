package com.daggle.animory.common.security;

import com.daggle.animory.common.error.exception.UnAuthorized401;
import com.daggle.animory.domain.account.entity.AccountRole;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
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

    public static final String TOKEN_PREFIX = "Bearer ";
    private static final String ROLES_CLAIM = "roles";


    // secretKey를 Base64로 인코딩
    @PostConstruct
    public void init() throws Exception {
        this.key = Base64.getEncoder().encodeToString(key.getBytes());
    }


    public String create(final String email, final AccountRole role) {
        final Date now = new Date(); // TODO: Date가 아닌 다른 방법을 찾아보라네요? (java.util... 대신 java.time ... 으로)
        final Date expiration = new Date(now.getTime() + tokenValiditySeconds * 1000);

        log.debug("expiration : " + expiration);

        return TOKEN_PREFIX + Jwts.builder().setSubject(email) // 정보 저장
            .claim(ROLES_CLAIM, role).setIssuedAt(new Date()) // 토큰 발행 시간
            .setExpiration(expiration) // 토큰 만료 시간
            .signWith(SignatureAlgorithm.HS256, key)  // 암호화 알고리즘 및 secretKey
            .compact();
    }

    // 토큰 검증, 인증 정보 조회
    public String getEmailFromToken(final String token) {
        try {
            return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject();

        } catch (final SignatureException ex) {
            log.error("Invalid JWT signature");
            throw new UnAuthorized401("Invalid JWT signature");
        } catch (final MalformedJwtException ex) {
            log.error("Invalid JWT token");
            throw new UnAuthorized401("Invalid JWT token");
        } catch (final ExpiredJwtException ex) {
            log.error("Expired JWT token");
            throw new UnAuthorized401("Expired JWT token");
        } catch (final UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
            throw new UnAuthorized401("Unsupported JWT token");
        } catch (final IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
            throw new UnAuthorized401("JWT claims string is empty.");
        } catch (final NullPointerException ex) {
            log.error("JWT RefreshToken is empty");
            throw new UnAuthorized401("JWT RefreshToken is empty");
        }
    }

    // 헤더에서 token 추출
    public String resolveToken(final String bearerToken) {
        if (!StringUtils.hasText(bearerToken) || !bearerToken.startsWith(TOKEN_PREFIX))
            throw new UnAuthorized401("토큰 형식이 올바르지 않습니다.");

        return bearerToken.substring(7);
    }


}