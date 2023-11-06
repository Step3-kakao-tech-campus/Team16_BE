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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class AccountTest extends AcceptanceTest {

    private final String EMAIL = AccountFixture.EMAIL;
    private final String PASSWORD = AccountFixture.PASSWORD;

    @Test
    void 이메일_중복_검사() throws Exception {
        final EmailValidateDto emailValidateDto = new EmailValidateDto(EMAIL + "1");

        result = mvc.perform(post("/account/email")
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .content(om.writeValueAsString(emailValidateDto)));

        assertSuccess();
    }

    @Test
    void 보호소_회원가입() throws Exception {
        final ShelterSignUpDto shelterSignUpDto = ShelterSignUpDto.builder()
            .email(EMAIL + "1")
            .password(PASSWORD)
            .name("테스트 보호소")
            .zonecode("12345")
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
    void 중복된_이메일로_회원가입_불가() throws Exception {
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
                                 .content(om.writeValueAsString(shelterSignUpDto)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    void 보호소_로그인() throws Exception {
        final AccountLoginDto accountLoginDto = AccountLoginDto.builder()
            .email(EMAIL)
            .password(PASSWORD)
            .build();

        result = mvc.perform(post("/account/login")
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .content(om.writeValueAsString(accountLoginDto)));

        assertSuccess();
        result.andExpect(jsonPath("$.response.accountId").value(1))
            .andExpect(jsonPath("$.response.accountInfo.id").value(1))
            .andExpect(jsonPath("$.response.accountInfo.role").value("SHELTER"))
            .andExpect(jsonPath("$.response.tokenExpirationDateTime").isNotEmpty());

        // header authorization field exists
        assertThat(result.andReturn()
                       .getResponse()
                       .getHeader("Authorization")).isNotNull();
    }

    @Test
    void 존재하지_않는_계정으로_로그인_실패와_안내문구() throws Exception {
        final AccountLoginDto accountLoginDto = AccountLoginDto.builder()
            .email(EMAIL + "1")
            .password(PASSWORD)
            .build();

        result = mvc.perform(post("/account/login")
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .content(om.writeValueAsString(accountLoginDto)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.success").value(false))
            .andExpect(jsonPath("$.error.message").value("이메일 또는 비밀번호를 확인해주세요."));

    }

}
