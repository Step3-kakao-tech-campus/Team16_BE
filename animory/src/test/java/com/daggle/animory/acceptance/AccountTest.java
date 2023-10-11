package com.daggle.animory.acceptance;

import com.daggle.animory.domain.account.dto.request.AccountLoginDto;
import com.daggle.animory.domain.account.dto.request.EmailValidateDto;
import com.daggle.animory.domain.account.dto.request.ShelterAddressSignUpDto;
import com.daggle.animory.domain.account.dto.request.ShelterSignUpDto;
import com.daggle.animory.domain.shelter.entity.Province;
import com.daggle.animory.testutil.AcceptanceTest;
import com.daggle.animory.testutil.fixture.AccountFixture;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Transactional
class AccountTest extends AcceptanceTest {

    private final String EMAIL = AccountFixture.EMAIL;
    private final String PASSWORD = AccountFixture.PASSWORD;

    @Test
    void 이메일_중복_검사() throws Exception {
        final EmailValidateDto emailValidateDto = new EmailValidateDto(EMAIL);

        result = mvc.perform(post("/account/email")
            .contentType(MediaType.APPLICATION_JSON)
            .content(om.writeValueAsString(emailValidateDto)));

        assertSuccess();
    }

    @Test
    void 보호소_회원가입() throws Exception {
        final ShelterSignUpDto shelterSignUpDto = ShelterSignUpDto.builder()
            .email(EMAIL)
            .password(PASSWORD)
            .name("테스트 보호소")
            .address(
                ShelterAddressSignUpDto.builder()
                    .province(Province.광주)
                    .city("무슨무슨구")
                    .roadName("무슨무슨로")
                    .detail("상세주소 1234-56")
                    .build()
            )
            .contact("010-1234-5678")
            .build();


        result = mvc.perform(post("/account/shelter")
            .contentType(MediaType.APPLICATION_JSON)
            .content(om.writeValueAsString(shelterSignUpDto)));

        assertSuccess();
    }

    @Test
    void 보호소_로그인() throws Exception {
        givenShelterAccount();

        final AccountLoginDto accountLoginDto = AccountLoginDto.builder()
            .email(EMAIL)
            .password(PASSWORD)
            .build();

        result = mvc.perform(post("/account/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(om.writeValueAsString(accountLoginDto)));

        assertSuccess();

        // header authorization field exists
        assertThat( result.andReturn()
            .getResponse()
            .getHeader("Authorization") ).isNotNull();
    }

}
