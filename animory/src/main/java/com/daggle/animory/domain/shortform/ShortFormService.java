package com.daggle.animory.domain.shortform;

import com.daggle.animory.domain.pet.repository.PetRepository;
import com.daggle.animory.domain.shortform.dto.request.ShortFormSearchCondition;
import com.daggle.animory.domain.shortform.dto.response.CategoryShortFormPage;
import com.daggle.animory.domain.shortform.dto.response.HomeShortFormPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShortFormService {

    private final PetRepository petRepository;

    public CategoryShortFormPage getCategoryShortFormPage(final ShortFormSearchCondition searchCondition,
                                                          final Pageable pageable) {
        return CategoryShortFormPage.of(
            buildCategoryPageTitle(searchCondition),
            petRepository.findSliceBy(
                searchCondition.type(),
                searchCondition.area(),
                pageable
            )
        );
    }

    public HomeShortFormPage getHomeShortFormPage(final Pageable pageable) {
        // TODO: 홈 화면 숏폼 영상은 어떤 순서, 어떤 기준으로 보여줄 것인가?
        return HomeShortFormPage.of(
            petRepository.findSliceBy(pageable) // TODO: 하드코딩된 Page 숫자
        );
    }


    private String buildCategoryPageTitle(final ShortFormSearchCondition searchCondition) {
        return searchCondition.area().getProvinceNameForUI() + " 기준 " + searchCondition.type().getKoreanName() + " 친구들";
    }
}
