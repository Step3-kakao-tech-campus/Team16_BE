package com.daggle.animory.domain.shortform;

import autoparams.AutoSource;
import autoparams.Repeat;
import com.daggle.animory.domain.pet.entity.PetType;
import com.daggle.animory.domain.shelter.entity.Province;
import com.daggle.animory.domain.shortform.controller.ShortFormController;
import com.daggle.animory.testutil.webmvctest.BaseWebMvcTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import javax.validation.constraints.Max;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Import(ShortFormController.class)
class ShortFormControllerTest extends BaseWebMvcTest {
    @MockBean
    private ShortFormService shortFormService;


    @Nested
    class 카테고리_숏폼_검색 {

        @ParameterizedTest(name = "성공 - 카테고리: {0}, 지역: {1}, 페이지: {2}")
        @AutoSource
        @Repeat
        void 성공_카테고리_숏폼_검색(final PetType type, final Province area, @Max(1000) final int page) throws Exception {
            mvc.perform(get("/short-forms")
                    .param("type", String.valueOf(type))
                    .param("area", String.valueOf(area))
                    .param("page", String.valueOf(page)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andDo(print());
        }

        @Test
        void 실패_페이지입력값은_음수이면안된다() throws Exception {
            mvc.perform(get("/short-forms")
                    .param("type", String.valueOf(PetType.DOG))
                    .param("area", String.valueOf(Province.경기))
                    .param("page", "-1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andDo(print());
        }

    }

    @Nested
    class 홈화면_숏폼_조회 {

        @ParameterizedTest(name = "성공 - 페이지: {0}")
        @AutoSource
        @Repeat
        void 성공_홈화면_숏폼_조회(@Max(1000) final int page) throws Exception {
            mvc.perform(get("/short-forms/home")
                    .param("page", String.valueOf(page)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
        }

        @Test
        void 실패_페이지입력값은_음수이면안된다() throws Exception {
            mvc.perform(get("/short-forms/home")
                    .param("page", "-1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false));
        }

    }
}