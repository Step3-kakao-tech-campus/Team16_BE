package com.daggle.animory.domain.shortform;

import com.daggle.animory.domain.pet.entity.PetVideo;
import com.daggle.animory.domain.shortform.dto.request.ShortFormSearchCondition;
import com.daggle.animory.domain.shortform.dto.response.CategoryShortFormPage;
import com.daggle.animory.domain.shortform.dto.response.HomeShortFormPage;
import com.daggle.animory.domain.shortform.repository.PetVideoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ShortFormService {

    private final PetVideoRepository petVideoRepository;

    public HomeShortFormPage getHomeShortFormPage(final Pageable pageable) {

        // Fetch Join + Pageable 동시에 수행하는 경우 발생하는 문제(HHH000104) 해결을 위해 쿼리를 두 개로 분할하였습니다.
        Slice<Integer> petVideoIdSlice = petVideoRepository.findSliceOfIds(pageable);
        List<PetVideo> petVideos = petVideoRepository.findAllByPetVideoIdIn(petVideoIdSlice.getContent());

        // LikeCount DESC 순서로 조회하고, 반환된 페이지(Slice)를 랜덤으로 섞는다.
        return HomeShortFormPage.of(
            shuffleVideos(petVideos),
            petVideoIdSlice.hasNext()
        );
    }

    public CategoryShortFormPage getCategoryShortFormPage(final ShortFormSearchCondition searchCondition,
                                                          final Pageable pageable) {

        Slice<Integer> petVideoIdSlice = petVideoRepository.findSliceOfIds(
            searchCondition.type(),
            searchCondition.area(),
            pageable
        );
        List<PetVideo> petVideos = petVideoRepository.findAllByPetVideoIdIn(petVideoIdSlice.getContent());

        return CategoryShortFormPage.of(
            buildCategoryPageTitle(searchCondition),
            shuffleVideos(petVideos),
            petVideoIdSlice.hasNext()
        );
    }

    // 랜덤으로 섞는다.
    private List<PetVideo> shuffleVideos(final List<PetVideo> petVideos) {
        Collections.shuffle(petVideos);
        return petVideos;
    }

    private String buildCategoryPageTitle(final ShortFormSearchCondition searchCondition) {
        return searchCondition.area().getProvinceNameForUI() + " 기준 " + searchCondition.type().getKoreanName() + " 친구들";
    }
}
