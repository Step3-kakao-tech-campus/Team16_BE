package com.daggle.animory.common.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TokenProvider {

    @Value("${jwt.secret}")
    private String key;

    @Value("${jwt.token-validity-in-seconds}")
    private long tokenValiditySeconds;

    private final UserDetailsService userDetailsService;
    public static final String TOKEN_PREFIX = "Bearer ";
    private static final String AUTHORITIES_KEY = "auth";


    // secretKey를 Base64로 인코딩
    @PostConstruct
    public void init() throws Exception {
        this.key = Base64.getEncoder().encodeToString(key.getBytes());
    }

    public String create(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Date now = new Date();

        return TOKEN_PREFIX + Jwts.builder()
                .setSubject(authentication.getName()) // 정보 저장
                .claim(AUTHORITIES_KEY, authorities)
                .setIssuedAt(new Date()) // 토큰 발행 시간
                .setExpiration(new Date(now.getTime() + tokenValiditySeconds)) // 토큰 만료 시간
                .signWith(SignatureAlgorithm.HS256, key)  // 암호화 알고리즘 및 secretKey
                .compact();
    }

    // 인증 정보 조회
    public Authentication getAuthentication(String token) {
        String accountSubject = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject();
        UserDetails userDetails = userDetailsService.loadUserByUsername(accountSubject);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 헤더에서 token 추출
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // 토큰의 유효 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(key).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
