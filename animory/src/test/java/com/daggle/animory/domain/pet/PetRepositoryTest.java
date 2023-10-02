package com.daggle.animory.domain.pet;

import com.daggle.animory.domain.pet.entity.Pet;
import com.daggle.animory.domain.pet.entity.PetType;
import com.daggle.animory.domain.pet.fixture.PetFixture;
import com.daggle.animory.domain.shelter.Province;
import com.daggle.animory.testutil.WithTimeSupportObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PetRepositoryTest extends WithTimeSupportObjectMapper {

    @Autowired
    private PetRepository petRepository;

    @Test
    void findSliceBy() {
        petRepository.saveAll(PetFixture.get(20, PetType.DOG, Province.광주));

        final Slice<Pet> slice = petRepository.findSliceBy(PetType.DOG, Province.광주, PageRequest.of(0, 10));

        print(slice);

        assertThat(slice.getContent()).hasSize(0); // TODO: Shelter Repository 구현 이후, Dummy Data 추가하여 테스트
    }

}