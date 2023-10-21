package com.daggle.animory.domain.shelter;

import autoparams.AutoSource;
import com.daggle.animory.testutil.webmvctest.BaseWebMvcTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import javax.validation.constraints.Min;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(ShelterController.class)
public class ShelterControllerTest extends BaseWebMvcTest {
    @MockBean
    private ShelterService shelterService;

    @Nested
    class 보호소_조회 {

        @ParameterizedTest
        @AutoSource
        void 성공_보호소_조회(@Min(0) int shelterId) throws Exception {
            mvc.perform(get("/shelter/{shelterId}", shelterId)
                            .param("page", "0"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.success").value(true))
                    .andDo(print());
        }

        @Test
        void 실패_보호소값은_음수이면안된다() throws Exception {
            mvc.perform(get("/shelter/{shelterId}", "-1")
                            .param("page", "0"))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false));
        }

        @Test
        void 실패_페이지입력값은_음수이면안된다() throws Exception {
            mvc.perform(get("/shelter/{shelterId}", "1")
                            .param("page", "-1"))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false));
        }
    }

    @Test
    void 보호소_위치_필터링() throws Exception {
        mvc.perform(post("/shelter/filter")
                .contentType("application/json")
                .content( om.writeValueAsBytes( List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10) ) ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andDo(print());
    }
}
