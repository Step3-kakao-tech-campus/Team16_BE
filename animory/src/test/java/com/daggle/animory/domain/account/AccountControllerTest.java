package com.daggle.animory.domain.account;

import com.daggle.animory.domain.account.dto.request.AccountLoginDto;
import com.daggle.animory.domain.account.dto.request.EmailValidateDto;
import com.daggle.animory.domain.account.dto.request.ShelterAddressSignUpDto;
import com.daggle.animory.domain.account.dto.request.ShelterSignUpDto;
import com.daggle.animory.domain.shelter.entity.Province;
import com.daggle.animory.testutil.webmvctest.BaseWebMvcTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import(AccountController.class)
public class AccountControllerTest extends BaseWebMvcTest {

    @MockBean
    AccountService accountService;

    @Nested
    class 회원가입 {
        @Test
        void 성공_보호소_회원가입() throws Exception {
            // given
            final ShelterSignUpDto shelterSignUpDto = ShelterSignUpDto.builder()
                    .name("animory")
                    .email("aaa@jnu.ac.kr")
                    .password("secreT123!")
                    .contact("01012345678")
                    .zonecode("3143")
                    .address(ShelterAddressSignUpDto.builder()
                            .province(Province.광주)
                            .city("북구")
                            .roadName("용봉동")
                            .detail("전남대")
                            .build())
                    .build();

            mvc.perform(post("/account/shelter")
                            .content(om.writeValueAsString(shelterSignUpDto))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.success").value(true))
                    .andDo(print());
        }

        @Test
        void 실패_이메일_형식_오류() throws Exception {
            // given
            final ShelterSignUpDto shelterSignUpDto = ShelterSignUpDto.builder()
                    .name("animory")
                    .email("aaajnu.ac.kr")
                    .password("secreR123!")
                    .contact("01012345678")
                    .zonecode("3143")
                    .address(ShelterAddressSignUpDto.builder()
                            .province(Province.광주)
                            .city("북구")
                            .roadName("용봉동")
                            .detail("전남대")
                            .build())
                    .build();

            mvc.perform(post("/account/shelter")
                            .content(om.writeValueAsString(shelterSignUpDto))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false))
                    .andDo(print());
        }

        @Test
        void 실패_비밀번호_형식_오류() throws Exception {
            // given
            final ShelterSignUpDto shelterSignUpDto = ShelterSignUpDto.builder()
                    .name("animory")
                    .email("aaa@jnu.ac.kr")
                    .password("secre123!")
                    .contact("01012345678")
                    .zonecode("3143")
                    .address(ShelterAddressSignUpDto.builder()
                            .province(Province.광주)
                            .city("북구")
                            .roadName("용봉동")
                            .detail("전남대")
                            .build())
                    .build();

            mvc.perform(post("/account/shelter")
                            .content(om.writeValueAsString(shelterSignUpDto))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false))
                    .andDo(print());
        }

        @Test
        void 실패_보호소_회원가입_주소null() throws Exception {
            // given
            final ShelterSignUpDto shelterSignUpDto = ShelterSignUpDto.builder()
                    .name("animory")
                    .email("aaa@jnu.ac.kr")
                    .password("secreT123!")
                    .contact("01012345678")
                    .zonecode("3143")
                    .address(null)
                    .build();

            mvc.perform(post("/account/shelter")
                            .content(om.writeValueAsString(shelterSignUpDto))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.error.message").value("보호소 주소를 입력해주세요."))
                    .andDo(print());
        }

        @Test
        void 실패_보호소_회원가입_주소_중_일부null() throws Exception {
            // given
            final ShelterSignUpDto shelterSignUpDto = ShelterSignUpDto.builder()
                    .name("animory")
                    .email("aaa@jnu.ac.kr")
                    .password("secreT123!")
                    .contact("01012345678")
                    .zonecode("3143")
                    .address(ShelterAddressSignUpDto.builder()
                            .province(null)
                            .city(null) // city는 null 허용
                            .roadName("용봉동")
                            .detail("전남대")
                            .build())
                    .build();

            mvc.perform(post("/account/shelter")
                            .content(om.writeValueAsString(shelterSignUpDto))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false))
                    .andDo(print());
        }
    }

    @Nested
    class 로그인 {
        @Test
        void 성공_로그인() throws Exception {
            // given
            final AccountLoginDto accountLoginDto = AccountLoginDto.builder()
                    .email("aaa@naver.com")
                    .password("asdfA123!")
                    .build();

            mvc.perform(post("/account/login")
                            .content(om.writeValueAsString(accountLoginDto))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.success").value(true))
                    .andExpect(header().exists("Authorization"))
                    .andDo(print());
        }

        @Test
        void 실패_이메일_널이면_안된다() throws Exception {
            // given
            final AccountLoginDto accountLoginDto = AccountLoginDto.builder()
                    .email(null)
                    .password("asdfA123")
                    .build();

            mvc.perform(post("/account/login")
                            .content(om.writeValueAsString(accountLoginDto))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false))
                    .andDo(print());
        }
        @Test
        void 실패_비밀번호_널이면_안된다() throws Exception {
            // given
            final AccountLoginDto accountLoginDto = AccountLoginDto.builder()
                    .email("dsfdf@dfa.com")
                    .password(null)
                    .build();

            mvc.perform(post("/account/login")
                            .content(om.writeValueAsString(accountLoginDto))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false))
                    .andDo(print());
        }
    }

    @Nested
    class 이메일_중복_검증 {
        @Test
        void 성공_이메일_중복_검증() throws Exception {
            // given
            final EmailValidateDto emailValidateDto = new EmailValidateDto("aaa@naver.com");

            mvc.perform(post("/account/email")
                            .content(om.writeValueAsString(emailValidateDto))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.success").value(true))
                    .andDo(print());
        }
    }
}
