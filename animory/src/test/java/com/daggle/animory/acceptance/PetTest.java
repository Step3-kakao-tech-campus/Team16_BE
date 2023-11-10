package com.daggle.animory.acceptance;

import com.daggle.animory.domain.pet.dto.PetPolygonProfileDto;
import com.daggle.animory.domain.pet.dto.request.PetRegisterRequestDto;
import com.daggle.animory.domain.pet.entity.AdoptionStatus;
import com.daggle.animory.domain.pet.entity.NeutralizationStatus;
import com.daggle.animory.domain.pet.entity.PetType;
import com.daggle.animory.domain.pet.entity.Sex;
import com.daggle.animory.testutil.AcceptanceTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import static org.springframework.http.HttpMethod.POST;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
class PetTest extends AcceptanceTest {

    final MockMultipartFile image = new MockMultipartFile("profileImage", "image.jpg", "image/jpeg",
                                                          "image".getBytes(StandardCharsets.UTF_8));
    final MockMultipartFile video = new MockMultipartFile("profileVideo", "video.mp4", "video/mp4",
                                                          "video".getBytes(StandardCharsets.UTF_8));

    @Nested
    class 강아지_등록 {
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

        @Test
        void 강아지를_한마리_등록한다() throws Exception {
            final String TOKEN = getShelterToken();

            final MockPart petInfoRequestPart = new MockPart("petInfo",
                                                             om.writeValueAsString(petInfo).getBytes(StandardCharsets.UTF_8));
            petInfoRequestPart.getHeaders().setContentType(MediaType.APPLICATION_JSON);

            result = mvc.perform(multipart(POST, "/pet")
                                     .file(image)
                                     .file(video)
                                     .part(petInfoRequestPart)
                                     .header("Authorization", TOKEN));

            assertSuccess();
        }

        /**
         * <pre>
         * 상세정보
         * 	동물 상세 정보 조회 API에서는
         * 	1. 보호중인 보호소의 정보를 알 수 있다.
         * 	2. 오각형으로 이뤄진 동물 특성을 알 수 있다.
         * 	3. 해당 동물의 프로필 사진 한 개를 볼 수 있다. </pre>
         */
        @Test
        void 펫_상세정보_조회() throws Exception {
            result = mvc.perform(get("/pet/{petId}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response.shelterInfo.id").isNotEmpty())
                .andExpect(jsonPath("$.response.shelterInfo.name").isNotEmpty())
                .andExpect(jsonPath("$.response.shelterInfo.contact").isNotEmpty())
                .andExpect(jsonPath("$.response.petPolygonProfileDto").isNotEmpty())
                .andExpect(jsonPath("$.response.petPolygonProfileDto.intelligence").value(3))
                .andExpect(jsonPath("$.response.profileImageUrl").isNotEmpty());
        }

        /**
         * 유기동물 프로필 리스트
         * 동물 기본 정보(식별자, 프로필 사진, 이름, 나이, 소속 보호소, 입양상태, 안락사 예정여부)를 확인할 수 있다.
         * 조회 기준은 둥록일 최신순 또는 보호만료일이 가까운 순으로 확인할 수 있다.
         * 더보기 버튼을 통해 최신순 또는 안락사 기간 기준으로 각각 전체 리스트를 확인할 수 있다.
         * 페이지 당 프로필을 각각 8개 단위로 확인할 수 있다.
         */
        @Test
        void 유기동물_프로필_리스트() throws Exception {
            result = mvc.perform(get("/pet/profiles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response.sosList").isNotEmpty())
                .andExpect(jsonPath("$.response.newList").isNotEmpty())
                // TODO:
                .andDo(print());
        }
    }
}
