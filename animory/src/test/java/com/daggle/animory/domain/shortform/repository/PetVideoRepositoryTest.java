package com.daggle.animory.domain.shortform.repository;

import com.daggle.animory.domain.pet.entity.PetType;
import com.daggle.animory.domain.pet.entity.PetVideo;
import com.daggle.animory.domain.shelter.entity.Province;
import com.daggle.animory.testutil.datajpatest.DataJpaTestWithDummyData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PetVideoRepositoryTest extends DataJpaTestWithDummyData {

    @Autowired
    private PetVideoRepository petVideoRepository;

    @Test
    void findSliceBy() {
        final Slice<Integer> petVideoIds = petVideoRepository.findSliceOfIds(PetType.DOG, Province.광주,
                                                                             PageRequest.of(0, 10));
        final List<PetVideo> petVideos = petVideoRepository.findAllByPetVideoIdIn(petVideoIds.getContent());

        assertThat(petVideos).hasSize(10);
    }


}
