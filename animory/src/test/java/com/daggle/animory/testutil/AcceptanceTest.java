package com.daggle.animory.testutil;

import com.daggle.animory.common.security.TokenProvider;
import com.daggle.animory.domain.account.AccountRepository;
import com.daggle.animory.domain.account.entity.Account;
import com.daggle.animory.domain.account.entity.AccountRole;
import com.daggle.animory.domain.fileserver.S3FileRepository;
import com.daggle.animory.domain.pet.entity.Pet;
import com.daggle.animory.domain.pet.entity.PetType;
import com.daggle.animory.domain.pet.repository.PetRepository;
import com.daggle.animory.domain.shelter.ShelterRepository;
import com.daggle.animory.domain.shelter.entity.Shelter;
import com.daggle.animory.testutil.fixture.AccountFixture;
import com.daggle.animory.testutil.fixture.PetFixture;
import com.daggle.animory.testutil.fixture.ShelterFixture;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
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

    // token

    @Autowired
    protected TokenProvider tokenProvider;

    protected String getShelterToken() {
        return tokenProvider.create(AccountFixture.EMAIL, AccountRole.SHELTER);
    }

    // dummy data setting

    @Autowired
    protected AccountRepository accountRepository;
    @Autowired
    protected ShelterRepository shelterRepository;
    @Autowired
    protected PetRepository petRepository;
    @MockBean
    protected S3FileRepository fileRepository;

    @Autowired
    private EntityManager em;


    @BeforeEach
    void setUpDummyData() {
        given(fileRepository.save(any()))
            .willReturn("https://animory.s3.ap-northeast-2.amazonaws.com/2021-09-01-1.jpg"); // mock S3

        resetDB();

        // 보호소 계정 생성
        final Account shelterAccount = accountRepository.save(AccountFixture.getShelter());

        // Shelter 등록
        final Shelter shelter = shelterRepository.save(ShelterFixture.getOne(shelterAccount)); // ShelterId = 1

        // Pet 1마리 등록
        final Pet firstPet = petRepository.save(PetFixture.getOne(shelter)); // PetId =  1

        // Pet 10마리 등록
        final List<Pet> pets = petRepository.saveAll(PetFixture.get(10, PetType.DOG, shelter));
    }

    private void resetDB() {
        em.createNativeQuery("""
            SET REFERENTIAL_INTEGRITY FALSE;
            TRUNCATE TABLE account RESTART IDENTITY;
            TRUNCATE TABLE shelter RESTART IDENTITY;
            TRUNCATE TABLE pet RESTART IDENTITY;
            SET REFERENTIAL_INTEGRITY TRUE;
        """).executeUpdate();
    }


}
