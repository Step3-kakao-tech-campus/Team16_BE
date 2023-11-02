package com.daggle.animory.domain.shortform.repository;

import com.daggle.animory.domain.pet.entity.PetType;
import com.daggle.animory.domain.pet.entity.PetVideo;
import com.daggle.animory.domain.shelter.entity.Province;
import com.daggle.animory.testutil.datajpatest.DataJpaTestWithDummyData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Import(PetVideoJpqlRepository.class) // JpaRepository가 아닌, @Repository(Component)라서 Bean 명시함.
class PetVideoJpqlRepositoryTest extends DataJpaTestWithDummyData {

    @Autowired
    private PetVideoJpqlRepository petVideoJpqlRepository;

    @Test
    void findSliceBy() {
        final Slice<Integer> petVideoIds = petVideoJpqlRepository.findPetVideoIdsBy(PetType.DOG, Province.광주,
                                                                                    PageRequest.of(0, 10));
        final List<PetVideo> petVideos = petVideoJpqlRepository.findAllByIds(petVideoIds.getContent());

        assertThat(petVideos).hasSize(10);
    }


}
