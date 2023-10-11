package com.daggle.animory.acceptance;

import com.daggle.animory.domain.pet.dto.PetPolygonProfileDto;
import com.daggle.animory.domain.pet.dto.request.PetRegisterRequestDto;
import com.daggle.animory.domain.pet.entity.AdoptionStatus;
import com.daggle.animory.domain.pet.entity.NeutralizationStatus;
import com.daggle.animory.domain.pet.entity.PetType;
import com.daggle.animory.domain.pet.entity.Sex;
import com.daggle.animory.testutil.AcceptanceTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import static org.springframework.http.HttpMethod.POST;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;

class PetTest extends AcceptanceTest {

    @Test
    void 강아지를_한마리_등록한다() throws Exception {
        final String TOKEN = getShelterToken();

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
            .header("Authorization", TOKEN ));

        assertSuccess();
    }




}