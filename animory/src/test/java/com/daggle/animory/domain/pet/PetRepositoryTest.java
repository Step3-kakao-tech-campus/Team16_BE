package com.daggle.animory.domain.pet;

import com.daggle.animory.domain.pet.entity.Pet;
import com.daggle.animory.domain.pet.entity.PetType;
import com.daggle.animory.domain.shelter.Province;
import com.daggle.animory.testutil.datajpatest.DataJpaTestWithDummyData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import static org.assertj.core.api.Assertions.assertThat;


class PetRepositoryTest extends DataJpaTestWithDummyData {

    @Autowired
    private PetRepository petRepository;

    @Test
    void findSliceBy() {
        final Slice<Pet> slice = petRepository.findSliceBy(PetType.DOG, Province.광주, PageRequest.of(0, 10));

        print(slice.getContent());

        assertThat(slice.getContent()).hasSize(10);
    }

}