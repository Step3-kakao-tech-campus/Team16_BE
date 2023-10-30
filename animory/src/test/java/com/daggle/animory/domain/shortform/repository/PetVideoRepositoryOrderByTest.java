package com.daggle.animory.domain.shortform.repository;

import com.daggle.animory.domain.pet.entity.PetVideo;
import com.daggle.animory.testutil.WithTimeSupportObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@Sql("classpath:sql/PetVideoRepositoryOrderByTest.sql")
@DataJpaTest
@EnableJpaAuditing
class PetVideoRepositoryOrderByTest extends WithTimeSupportObjectMapper {
    @Autowired
    private PetVideoRepository petVideoRepository;

    @Test
    void 홈화면_숏폼조회쿼리_테스트() {
        Slice<Integer> petVideoIds = petVideoRepository.findSliceOfIds(PageRequest.of(0, 10));
        List<PetVideo> petVideos = petVideoRepository.findAllByPetVideoIdIn(petVideoIds.getContent());
        print(petVideos);

        assertThat(petVideos.get(0).getLikeCount()).isEqualTo(5000);
    }
}
