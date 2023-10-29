package com.daggle.animory.acceptance;

import com.daggle.animory.testutil.AcceptanceTest;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ShortFormTest extends AcceptanceTest {

    /**
     * 홈
     * 숏폼 비디오 API는 회원 권한을 요구하지 않는다.
     * 숏폼 비디오 응답에는 각각 Pet 식별자가 포함되어 있다.
     * 숏폼 비디오 응답에는 해당 보호소 식별자, 보호소 이름도 포함되어 있다.
     * 숏폼 비디오 응답에서 해당 동물의 입양여부를 알 수 있다.
     */
    @Test
    void 홈_화면_숏폼API() throws Exception {
        mvc.perform(get("/short-forms/home")
                        .param("page", "0"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.response.shortForms[0].petId").isNotEmpty())
            .andExpect(jsonPath("$.response.shortForms[0].name").isNotEmpty())
            .andExpect(jsonPath("$.response.shortForms[0].age").isNotEmpty())
            .andExpect(jsonPath("$.response.shortForms[0].shelterId").isNotEmpty())
            .andExpect(jsonPath("$.response.shortForms[0].shelterName").isNotEmpty())
            .andExpect(jsonPath("$.response.shortForms[0].profileShortFormUrl").isNotEmpty())
            .andExpect(jsonPath("$.response.shortForms[0].likeCount").isNotEmpty())
            .andExpect(jsonPath("$.response.shortForms[0].adoptionStatus").isNotEmpty())
            .andExpect(jsonPath("$.response.hasNext").value(true))
            .andDo(print());
    }


    /**
     * 카테고리
     * 숏폼 영상 검색 조건에서 동물 타입을 선택할 수 있다.
     * 지역 카테고리도 검색할 수 있다.
     * 숏폼 영상 응답에 동물 정보가 포함되어 있다.
     */
    @Test
    void 카테고리_숏폼API() throws Exception {
        mvc.perform(get("/short-forms")
                        .param("page", "0")
                        .param("type", "DOG")
                        .param("area", "광주"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.response.categoryTitle").isNotEmpty())
            .andExpect(jsonPath("$.response.shortForms[0].petId").isNotEmpty())
            .andExpect(jsonPath("$.response.shortForms[0].name").isNotEmpty())
            .andExpect(jsonPath("$.response.shortForms[0].age").isNotEmpty())
            .andExpect(jsonPath("$.response.shortForms[0].shelterId").isNotEmpty())
            .andExpect(jsonPath("$.response.shortForms[0].shelterName").isNotEmpty())
            .andExpect(jsonPath("$.response.shortForms[0].profileShortFormUrl").isNotEmpty())
            .andExpect(jsonPath("$.response.shortForms[0].adoptionStatus").isNotEmpty())
            .andExpect(jsonPath("$.response.hasNext").value(true))
            .andDo(print());
    }
}
