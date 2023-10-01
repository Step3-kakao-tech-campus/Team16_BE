package com.daggle.animory.domain.pet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;



@AutoConfigureMockMvc
@SpringBootTest
class PetControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    void fileInputTest() throws Exception{
        final MockMultipartFile video = new MockMultipartFile(
                "profileVideo", "test1.mp4","video/mp4", "test file1".getBytes(StandardCharsets.UTF_8)
        );
        final MockMultipartFile image = new MockMultipartFile(
                "profileImage", "test2.jpeg","image/jpeg", "test file2".getBytes(StandardCharsets.UTF_8)
        );


        final ResultActions resultActions = mvc.perform(
                multipart("/pet")
                        .file(video)
                        .file(image)
        );

        final String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        resultActions.andExpect(jsonPath("$.success").value("false"));
    }

    @Test
    void getPetProfiles() throws Exception {
        mvc.perform(get("/pet/profiles"))
            .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    void getPetSosProfiles() throws Exception {
        mvc.perform(get("/pet/profiles/sos"))
            .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    void getPetNewProfiles() throws Exception {
        mvc.perform(get("/pet/profiles/new"))
            .andExpect(jsonPath("$.success").value(false));
    }

    @ParameterizedTest
    @CsvSource(value = { "1", "2", "3", "4", "5", "6", "7", "8", "9" })
    void getPetDetail(final int petId) throws Exception {
        mvc.perform(get("/pet/{petId}", petId))
            .andExpect(jsonPath("$.success").value(false));
    }
}