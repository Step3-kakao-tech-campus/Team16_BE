package com.daggle.animory.testutil;

import com.daggle.animory.domain.account.AccountRepository;
import com.daggle.animory.testutil.fixture.AccountFixture;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public abstract class AcceptanceTest {
    @Autowired
    protected MockMvc mvc;

    protected ObjectMapper om = new ObjectMapper()
        .registerModule(new JavaTimeModule());


    protected ResultActions result;

    protected void assertSuccess() throws Exception {
        result.andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true))
            .andDo(print());
    }

    // dummy data setting

    @Autowired
    protected AccountRepository accountRepository;

    protected void givenShelterAccount(){
        accountRepository.save(AccountFixture.getShelter());
    }

}
