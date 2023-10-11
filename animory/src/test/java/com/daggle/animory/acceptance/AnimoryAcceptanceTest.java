package com.daggle.animory.acceptance;

import com.daggle.animory.domain.account.dto.request.AccountLoginDto;
import com.daggle.animory.domain.account.dto.request.EmailValidateDto;
import com.daggle.animory.domain.account.dto.request.ShelterAddressSignUpDto;
import com.daggle.animory.domain.account.dto.request.ShelterSignUpDto;
import com.daggle.animory.domain.pet.dto.PetPolygonProfileDto;
import com.daggle.animory.domain.pet.dto.request.PetRegisterRequestDto;
import com.daggle.animory.domain.pet.entity.AdoptionStatus;
import com.daggle.animory.domain.pet.entity.NeutralizationStatus;
import com.daggle.animory.domain.pet.entity.PetType;
import com.daggle.animory.domain.pet.entity.Sex;
import com.daggle.animory.domain.shelter.entity.Province;
import com.daggle.animory.testutil.AcceptanceTest;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * <pre>
 *
 * A. 보호소 계정 회원가입, 로그인
 *
 * B. 펫 등록, 수정
 *
 *
 *
 *
 *
 * </pre>
 */
@Slf4j
@Transactional
class AnimoryAcceptanceTest extends AcceptanceTest {

    private final Map<String, String> client = new HashMap<>();

    @Test
    void A_보호소_회원가입_로그인() throws Exception {
        계정에_사용할_이메일과_비밀번호를_결정한다();

        이메일_중복_검사();

        보호소_회원가입();

        보호소_로그인();

        토큰을_저장한다();
    }

    @Test
    void B_펫_등록_수정() throws Exception {
        A_보호소_회원가입_로그인();

        강아지를_한마리_등록한다();

        등록한_강아지의_ID를_받는다();

        등록한_강아지_상세정보를_보고_맞는지_확인한다();
    }


    // Usecases


    // Account
    void 계정에_사용할_이메일과_비밀번호를_결정한다() {
        client.put("email", "Test1234@gmail.com");
        client.put("password", "Test1234@");
    }

    void 이메일_중복_검사() throws Exception {
        final EmailValidateDto emailValidateDto = new EmailValidateDto(client.get("email"));

        result = mvc.perform(post("/account/email")
            .contentType(MediaType.APPLICATION_JSON)
            .content(om.writeValueAsString(emailValidateDto)));

        assertSuccess();
    }

    void 보호소_회원가입() throws Exception {
        final ShelterSignUpDto shelterSignUpDto = ShelterSignUpDto.builder()
            .email(client.get("email"))
            .password(client.get("password"))
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

    void 보호소_로그인() throws Exception {
        final AccountLoginDto accountLoginDto = AccountLoginDto.builder()
            .email(client.get("email"))
            .password(client.get("password"))
            .build();

        result = mvc.perform(post("/account/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(om.writeValueAsString(accountLoginDto)));

        assertSuccess();
    }

    void 토큰을_저장한다() {
        client.put("token", result.andReturn()
            .getResponse()
            .getHeader("Authorization"));

        assertThat(client.get("token")).isNotNull();
    }



    // Pet
    void 강아지를_한마리_등록한다() throws Exception {
        final PetRegisterRequestDto petInfo = PetRegisterRequestDto.builder()
            .name("뽀삐")
            .age("0년3개월")
            .type(PetType.DOG)
            .weight(3.5f)
            .size("태어난지 얼마 안되서 작음")
            .sex(Sex.MALE)
            .vaccinationStatus("아직 접종 안함")
            .adoptionStatus(AdoptionStatus.NO)
            .neutralizationStatus(NeutralizationStatus.NO)
            .protectionExpirationDate(LocalDate.now().plusMonths(6))
            .description("뽀삐는 아직 어린 아이라서 많이 놀아줘야해요.")
            .petPolygonProfileDto(
                PetPolygonProfileDto.builder()
                    .activeness(1)
                    .adaptability(1)
                    .affinity(3)
                    .athletic(1)
                    .intelligence(4)
                    .build()
            )
            .build();
        final MockPart petInfoRequestPart = new MockPart("petInfo", om.writeValueAsString(petInfo).getBytes(StandardCharsets.UTF_8));
        petInfoRequestPart.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        final MockMultipartFile image = new MockMultipartFile("profileImage", "image.jpg", "image/jpeg", "image".getBytes(StandardCharsets.UTF_8));
        final MockMultipartFile video = new MockMultipartFile("profileVideo", "video.mp4", "video/mp4", "video".getBytes(StandardCharsets.UTF_8));

        result = mvc.perform(multipart(POST, "/pet")
            .file(image)
            .file(video)
            .part(petInfoRequestPart)
            .header("Authorization", client.get("token")));

        assertSuccess();
    }

    void 등록한_강아지의_ID를_받는다() throws Exception {
        final int petId = new JSONObject(result.andReturn()
            .getResponse()
            .getContentAsString(StandardCharsets.UTF_8))
            .getJSONObject("response")
            .getInt("petId");

        client.put("petId", String.valueOf(petId));
    }

    void 등록한_강아지_상세정보를_보고_맞는지_확인한다() throws Exception {
        result = mvc.perform(get("/pet/{petId}", client.get("petId")));

        assertSuccess();

        result.andExpect(jsonPath("$.response.name").value("뽀삐"))
            .andExpect(jsonPath("$.response.age").value("0년3개월"))
            .andExpect(jsonPath("$.response.type").value(PetType.DOG.name()));
    }



}
