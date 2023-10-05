package com.daggle.animory.domain.shortform;

import com.daggle.animory.common.Response;
import com.daggle.animory.domain.shortform.dto.request.ShortFormSearchCondition;
import com.daggle.animory.domain.shortform.dto.response.CategoryShortFormPage;
import com.daggle.animory.domain.shortform.dto.response.HomeShortFormPage;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@Validated
@RestController
@RequiredArgsConstructor
public class ShortFormController implements ShortFormControllerApi {

    private final ShortFormService shortFormService;

    /**
     * 다양한 검색 조건 변화에 대응할 수 있는 목적으로 사용할 숏폼 영상 조회 API 입니다.
     */
    @GetMapping("/short-forms")
    public Response<CategoryShortFormPage> getShortForms(@ModelAttribute @Valid final ShortFormSearchCondition searchCondition) {
        return Response.success( shortFormService.getCategoryShortFormPage(searchCondition) );
    }

    /**
     * 홈 화면에서 보여 줄 숏폼 영상들을 조회합니다.
     */
    @GetMapping("/short-forms/home")
    public Response<HomeShortFormPage> getHomeShortForms(@RequestParam("page") @Min(0) final int page) {
        return Response.success( shortFormService.getHomeShortFormPage(page) );
    }

}
