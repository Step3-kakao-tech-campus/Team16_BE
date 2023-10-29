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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ShortFormService {

    private final PetVideoRepository petVideoRepository;

    public HomeShortFormPage getHomeShortFormPage(final Pageable pageable) {
        // LikeCount DESC 순서로 조회하고, 반환된 페이지(Slice)를 랜덤으로 섞는다.
        Slice<PetVideo> petVideoSlice = petVideoRepository.findSliceBy(pageable);

        return HomeShortFormPage.of(
            shuffleVideos(petVideoSlice),
            petVideoSlice.hasNext()
        );
    }

    public CategoryShortFormPage getCategoryShortFormPage(final ShortFormSearchCondition searchCondition,
                                                          final Pageable pageable) {
        Slice<PetVideo> petVideoSlice = petVideoRepository.findSliceBy(
            searchCondition.type(),
            searchCondition.area(),
            pageable
        );

        return CategoryShortFormPage.of(
            buildCategoryPageTitle(searchCondition),
            shuffleVideos(petVideoSlice),
            petVideoSlice.hasNext()
        );
    }

    // 랜덤으로 섞는다.
    private List<PetVideo> shuffleVideos(final Slice<PetVideo> petVideoSlice) {
        List<PetVideo> petVideos = new ArrayList<>(petVideoSlice.getContent());
        Collections.shuffle(petVideos);
        return petVideos;
    }

    private String buildCategoryPageTitle(final ShortFormSearchCondition searchCondition) {
        return searchCondition.area().getProvinceNameForUI() + " 기준 " + searchCondition.type().getKoreanName() + " 친구들";
    }
}
