package com.daggle.animory.domain.pet;

import com.daggle.animory.domain.pet.entity.Pet;
import com.daggle.animory.domain.pet.entity.PetType;
import com.daggle.animory.domain.pet.repository.PetRepository;
import com.daggle.animory.domain.shelter.entity.Province;
import com.daggle.animory.testutil.datajpatest.DataJpaTestWithDummyData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import java.util.List;

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

    @Test
    void findByShelterId() {
        final Integer shelterId = 1;
        final Page<Pet> page = petRepository.findByShelterId(shelterId, PageRequest.of(0, 10));

        final List<Pet> pets = page.getContent();
        print(pets);

        pets.forEach(
                p -> assertThat(p.getShelter().getId()).isEqualTo(shelterId)
        );
    }
}