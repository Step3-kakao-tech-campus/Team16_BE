package com.daggle.animory.domain.pet;

import com.daggle.animory.domain.pet.controller.PetController;
import com.daggle.animory.domain.pet.dto.request.PetRegisterRequestDto;
import com.daggle.animory.domain.pet.entity.AdoptionStatus;
import com.daggle.animory.domain.pet.entity.PetType;
import com.daggle.animory.domain.pet.entity.Sex;
import com.daggle.animory.domain.pet.service.PetReadService;
import com.daggle.animory.domain.pet.service.PetWriteService;
import com.daggle.animory.testutil.webmvctest.BaseWebMvcTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import static org.springframework.http.HttpMethod.POST;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@Import(PetController.class)
class PetControllerTest extends BaseWebMvcTest {

    @MockBean
    private PetReadService petReadService;

    @MockBean
    private PetWriteService petWriteService;

    @Nested
    class 펫_쓰기 {
        @Test
        void registerPet() throws Exception {
            final PetRegisterRequestDto petRegisterRequestDto = PetRegisterRequestDto.builder()
                .name("테스트")
                .age("1년2개월")
                .type(PetType.DOG)
                .weight(1.0f)
                .size("작음")
                .sex(Sex.MALE)
                .vaccinationStatus("접종완료")
                .adoptionStatus(AdoptionStatus.YES)
                .protectionExpirationDate(LocalDate.now().plusMonths(6))
                .description("테스트용 펫입니다.")
                .build();

            final MockPart mockPart = new MockPart("petInfo", om.writeValueAsString(petRegisterRequestDto).getBytes(StandardCharsets.UTF_8));
            mockPart.getHeaders().setContentType(MediaType.APPLICATION_JSON);

            mvc.perform(multipart(POST, "/pet")
                    .file(getMockImage())
                    .file(getMockVideo())
                    .part(mockPart))
                .andExpect(jsonPath("$.success").value(true));
        }

        @Test
        void updatePet() throws Exception {
            mvc.perform(patch("/pet")
                    .contentType("multipart/form-data"))
                .andExpect(jsonPath("$.success").value(false));
        }
    }


    @Nested
    class 펫_읽기 {
        @Test
        void getPetProfiles() throws Exception {
            mvc.perform(get("/pet/profiles"))
                .andExpect(jsonPath("$.success").value(true));
        }

        @Test
        void getPetSosProfiles() throws Exception {
            mvc.perform(get("/pet/profiles/sos"))
                .andExpect(jsonPath("$.success").value(true));
        }

        @Test
        void getPetNewProfiles() throws Exception {
            mvc.perform(get("/pet/profiles/new"))
                .andExpect(jsonPath("$.success").value(true));
        }

        @ParameterizedTest
        @CsvSource(value = {"1", "2", "3", "4", "5", "6", "7", "8", "9"})
        void getPetDetail(final int petId) throws Exception {
            mvc.perform(get("/pet/{petId}", petId))
                .andExpect(jsonPath("$.success").value(true));
        }
    }


    private MockMultipartFile getMockImage() {
        return new MockMultipartFile("profileImage", "test.jpg", "image/jpeg", "test".getBytes(StandardCharsets.UTF_8));
    }

    private MockMultipartFile getMockVideo() {
        return new MockMultipartFile("profileVideo", "test.mp4", "video/mp4", "test".getBytes(StandardCharsets.UTF_8));

    }

}