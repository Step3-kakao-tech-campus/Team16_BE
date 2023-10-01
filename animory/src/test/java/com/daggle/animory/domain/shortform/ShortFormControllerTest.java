package com.daggle.animory.domain.shortform;

import com.daggle.animory.common.config.SpringSecurityConfiguration;
import com.daggle.animory.domain.shelter.Province;
import com.daggle.animory.domain.pet.entity.PetType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({ ShortFormController.class })
@AutoConfigureMockMvc
@Import(SpringSecurityConfiguration.class) // TODO: Base Test Class 상속하는 방식으로..
class ShortFormControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ShortFormService shortFormService;


    @Nested
    class 카테고리_숏폼_검색 {

        @ParameterizedTest(name = "성공 - 카테고리: {0}, 지역: {1}, 페이지: {2}")
        @CsvSource(textBlock = """            
            CAT,서울,0
            DOG,광주,1
            CAT,부산,25
            DOG,대구,30
            CAT,대전,50
            DOG,울산,100
            CAT,인천,200
            DOG,경기,300
            CAT,강원,400
            DOG,충북,500
            CAT,충남,600
            DOG,전북,700
            CAT,전남,800
            DOG,경북,900
            CAT,경남,1000
            DOG,제주,2000
            CAT,세종,3000000          
            """)
        void 성공_카테고리_숏폼_검색(PetType type, Province area, int page) throws Exception {
            mvc.perform(get("/short-forms")
                .param("type", String.valueOf(type))
                .param("area", String.valueOf(area))
                .param("page", String.valueOf(page)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true));
        }

        // TODO: POST 이외 METHOD Validation Handler 가 작성되어야 Query Parameter Error 시 Response 객체를 테스트 할 수 있음.
    }

    @Nested
    class 홈화면_숏폼_조회 {

        @ParameterizedTest(name = "성공 - 페이지: {0}")
        @CsvSource(value = { "0", "11", "23333", "3400", "421", "5", "6616", "7", "8", "900" })
        void 성공_홈화면_숏폼_조회(int page) throws Exception {
            mvc.perform(get("/short-forms/home")
                .param("page", String.valueOf(page)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true));
        }

    }
}