package com.daggle.animory.common.security;

import com.daggle.animory.domain.account.entity.AccountRole;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = TokenProvider.class)
class TokenProviderTest {

    @Autowired
    private TokenProvider tokenProvider;

    @Test
    void 토큰_만료기한이_올바르게_설정되는지_검사() throws JSONException {
        final LocalDateTime now = LocalDateTime.now();
        final String token = tokenProvider.create("test@gmail.com", AccountRole.SHELTER);

        final String decodedToken = new String(java.util.Base64.getDecoder().decode(token.split("\\.")[1]));
        final String expirationTimeOfToken = new JSONObject(decodedToken).get("exp").toString();

        final LocalDateTime expectedExpirationTime = LocalDateTime
            .ofInstant(
                Instant.ofEpochSecond(Long.parseLong(expirationTimeOfToken)),
                java.time.ZoneId.systemDefault()
            );

        assertThat(now.plusDays(1).withHour(3).truncatedTo(ChronoUnit.MINUTES))
            .isEqualTo(expectedExpirationTime.truncatedTo(ChronoUnit.MINUTES));
    }


}