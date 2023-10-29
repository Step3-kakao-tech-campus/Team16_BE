package com.daggle.animory.domain.shortform.repository;

import com.daggle.animory.domain.pet.entity.PetType;
import com.daggle.animory.domain.pet.entity.PetVideo;
import com.daggle.animory.domain.shelter.entity.Province;
import com.daggle.animory.testutil.datajpatest.DataJpaTestWithDummyData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import static org.assertj.core.api.Assertions.assertThat;

class PetVideoRepositoryTest extends DataJpaTestWithDummyData {

    @Autowired
    private PetVideoRepository petVideoRepository;

    @Test
    void findSliceBy() {
        final Slice<PetVideo> slice = petVideoRepository.findSliceBy(PetType.DOG, Province.광주, PageRequest.of(0, 10));

        print(slice.getContent());

        assertThat(slice.getContent()).hasSize(10);
    }


}