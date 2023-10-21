package com.daggle.animory.domain.shortform.controller;

import com.daggle.animory.common.Response;
import com.daggle.animory.domain.shortform.dto.request.ShortFormSearchCondition;
import com.daggle.animory.domain.shortform.dto.response.CategoryShortFormPage;
import com.daggle.animory.domain.shortform.dto.response.HomeShortFormPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@Tag(name = "숏폼 비디오 API", description = "숏폼 비디오 관련 API 입니다.")
public interface ShortFormControllerApi {

    @Operation(summary = "조건부 숏폼 비디오 탐색",
        description = "다양한 검색 조건 변화에 대응할 수 있는 목적으로 사용할 숏폼 비디오 조회 API 입니다.")
    @GetMapping("/short-forms")
    Response<CategoryShortFormPage> getShortForms(@ModelAttribute @Valid ShortFormSearchCondition searchCondition);


    @Operation(summary = "홈 화면 숏폼 비디오 조회", description = "홈 화면에서 보여 줄 숏폼 비디오들을 조회합니다.")
    @GetMapping("/short-forms/home")
    Response<HomeShortFormPage> getHomeShortForms(@RequestParam("page") @Min(0) int page);

}
