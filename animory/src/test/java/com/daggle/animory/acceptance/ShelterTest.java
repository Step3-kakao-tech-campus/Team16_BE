package com.daggle.animory.acceptance;

import com.daggle.animory.testutil.AcceptanceTest;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ShelterTest extends AcceptanceTest {

    /**
     근처 동물 보호소 보기 (지도 페이지)
     입력받은 주소(kakao id)들 중 DB에 존재하는 주소(kakao id)들을 찾아서 필터링하여 응답한다.
     */
    @Test
    void 근처_동물_보호소_보기() throws Exception {
        mvc.perform(post("/shelter/filter")
            .contentType("application/json")
            .content("[14569757, 14569758, 14569759]"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.response").isArray())
            .andExpect(jsonPath("$.response[0].kakaoLocationId").value(14569757));
    }

    /**
     보호소 상세 페이지(프로필)
         보호소 기본 정보(이름, 주소, 연락처)를 확인할 수 있다.
         해당 보호소가 보호중인 동물들을 확인할 수 있다. (유기동물 프로필 리스트 페이지와 동일하게 보여진다)
         앱에서 전화걸기를 클릭하면 전화번호가 뜬다.
     */
    @Test
    void 보호소_상세_페이지() throws Exception {
        mvc.perform(get("/shelter/{shelterId}", 1)
            .param("page", "0"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.response.shelter.name").value("광주광역시동물보호소"))
            .andDo(print());
    }
}
