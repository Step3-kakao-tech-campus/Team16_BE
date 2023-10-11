package com.daggle.animory.testutil.datajpatest;


import com.daggle.animory.testutil.fixture.AccountFixture;
import com.daggle.animory.testutil.fixture.ShelterFixture;
import com.daggle.animory.domain.pet.repository.PetRepository;
import com.daggle.animory.domain.pet.entity.Pet;
import com.daggle.animory.domain.pet.entity.PetType;
import com.daggle.animory.testutil.fixture.PetFixture;
import com.daggle.animory.domain.shelter.ShelterRepository;
import com.daggle.animory.domain.shelter.entity.Province;
import com.daggle.animory.domain.shelter.entity.Shelter;
import com.daggle.animory.testutil.WithTimeSupportObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;

@DataJpaTest
@EnableJpaAuditing
public abstract class DataJpaTestWithDummyData extends WithTimeSupportObjectMapper {

    @Autowired
    private ShelterRepository shelterRepository;

    @Autowired
    private PetRepository petRepository;

    @BeforeEach
    void setUp() {
        final List<Shelter> shelters = ShelterFixture.get(5, Province.광주, AccountFixture.getShelter());

        shelterRepository.saveAll(shelters);

        final List<Pet> pets = PetFixture.get(20, PetType.DOG, shelters.get(0)); // shelter province = 광주
        final List<Pet> petsOfOtherShelter = PetFixture.get(10, PetType.DOG, shelters.get(1)); // shelter province = 광주

        petRepository.saveAll(pets);
        petRepository.saveAll(petsOfOtherShelter);
    }
}
