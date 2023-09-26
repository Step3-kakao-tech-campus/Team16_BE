package com.daggle.animory.shortform;

import com.daggle.animory.common.Response;
import com.daggle.animory.shortform.dto.request.ShortFormSearchCondition;
import com.daggle.animory.shortform.dto.response.ShortFormPage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ShortFormController {

    private final ShortFormService shortFormService;

    /**
     * 다양한 검색 조건 변화에 대응할 수 있는 목적으로 사용할 숏폼 영상 조회 API 입니다.
     */
    @GetMapping("/short-forms")
    public Response<ShortFormPage> getShortForms(@ModelAttribute final ShortFormSearchCondition condition) {
        return Response.success( shortFormService.getShortFormPage(condition) );
    }

    /**
     * 홈 화면에서 보여 줄 숏폼 영상들을 조회합니다.
     */
    @GetMapping("/short-forms/home")
    public String getHomeShortForms() {
        return "short-forms";
    }








}
