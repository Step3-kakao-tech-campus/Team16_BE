package com.daggle.animory.domain.pet;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.nio.charset.StandardCharsets;



@AutoConfigureMockMvc
@SpringBootTest
class PetControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    public void fileInputTest() throws Exception{
        MockMultipartFile video = new MockMultipartFile(
                "video", "test1.mp4","video/mp4", "test file1".getBytes(StandardCharsets.UTF_8)
        );
        MockMultipartFile image = new MockMultipartFile(
                "image", "test2.jpeg","image/jpeg", "test file2".getBytes(StandardCharsets.UTF_8)
        );
        MockMultipartFile json = new MockMultipartFile(
                "key", "test3.json","application/json", "test json".getBytes(StandardCharsets.UTF_8)
        );


        ResultActions resultActions = mvc.perform(
                multipart("/pet")
                        .file(video)
                        .file(image)
                        .file(json)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        resultActions.andExpect(jsonPath("$.success").value("true"));
    }
}