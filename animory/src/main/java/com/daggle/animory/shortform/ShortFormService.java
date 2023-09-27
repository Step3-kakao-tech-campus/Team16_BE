package com.daggle.animory.shortform;

import com.daggle.animory.shortform.dto.request.ShortFormSearchCondition;
import com.daggle.animory.shortform.dto.response.CategoryShortFormPage;
import com.daggle.animory.shortform.dto.response.HomeShortFormPage;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;

@Service
public class ShortFormService {

    public CategoryShortFormPage getCategoryShortFormPage(final ShortFormSearchCondition condition) {
        throw new NotImplementedException("getShortFormPage");
    }

    public HomeShortFormPage getHomeShortFormPage(final int page) {
        throw new NotImplementedException("getHomeShortFormPage");
    }
}
