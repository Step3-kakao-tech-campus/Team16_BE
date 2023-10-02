package com.daggle.animory.domain.shortform;

import com.daggle.animory.domain.pet.PetRepository;
import com.daggle.animory.domain.shortform.dto.request.ShortFormSearchCondition;
import com.daggle.animory.domain.shortform.dto.response.CategoryShortFormPage;
import com.daggle.animory.domain.shortform.dto.response.HomeShortFormPage;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShortFormService {

    private final PetRepository petRepository;

    public CategoryShortFormPage getCategoryShortFormPage(final ShortFormSearchCondition searchCondition) {
        return CategoryShortFormPage.of(
            searchCondition,
            petRepository.findSliceBy(
                searchCondition.type(),
                searchCondition.area(),
                searchCondition.pageable()
            )
        );
    }

    public HomeShortFormPage getHomeShortFormPage(final int page) {
        throw new NotImplementedException("NotImplemented yet");
    }
}
