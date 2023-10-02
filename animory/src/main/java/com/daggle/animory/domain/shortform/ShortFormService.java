package com.daggle.animory.domain.shortform;

import com.daggle.animory.domain.shortform.dto.request.ShortFormSearchCondition;
import com.daggle.animory.domain.shortform.dto.response.CategoryShortFormPage;
import com.daggle.animory.domain.shortform.dto.response.HomeShortFormPage;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;

@Service
public class ShortFormService {

    public CategoryShortFormPage getCategoryShortFormPage(final ShortFormSearchCondition condition) {
        throw new NotImplementedException("NotImplemented yet");
    }

    public HomeShortFormPage getHomeShortFormPage(final int page) {
        throw new NotImplementedException("NotImplemented yet");
    }
}
