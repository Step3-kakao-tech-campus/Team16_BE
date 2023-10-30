package com.daggle.animory.domain.shortform.controller;

import com.daggle.animory.common.Response;
import com.daggle.animory.common.security.UserDetailsImpl;
import com.daggle.animory.domain.shortform.ShortFormService;
import com.daggle.animory.domain.shortform.dto.request.ShortFormSearchCondition;
import com.daggle.animory.domain.shortform.dto.response.CategoryShortFormPage;
import com.daggle.animory.domain.shortform.dto.response.HomeShortFormPage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
@RequiredArgsConstructor
public class ShortFormController implements ShortFormControllerApi {

    private final ShortFormService shortFormService;

    /**
     * 다양한 검색 조건 변화에 대응할 수 있는 목적으로 사용할 숏폼 영상 조회 API 입니다.
     */
    @Override
    @GetMapping("/short-forms")
    public Response<CategoryShortFormPage> getShortForms(
            @ModelAttribute @Valid final ShortFormSearchCondition searchCondition,
            @PageableDefault final Pageable pageable) {
        return Response.success(shortFormService.getCategoryShortFormPage(searchCondition, pageable));
    }

    /**
     * 홈 화면에서 보여 줄 숏폼 영상들을 조회합니다.
     */
    @GetMapping("/short-forms/home")
    public Response<HomeShortFormPage> getHomeShortForms(@PageableDefault final Pageable pageable) {
        return Response.success(shortFormService.getHomeShortFormPage(pageable));
    }

    @PostMapping("/like/{petVideoId}")
    public Response<Void> increasePetLikeCount(@PathVariable final int petVideoId) {
        shortFormService.updatePetVideoLikeCount(petVideoId);
        return Response.success();
    }

    @DeleteMapping("/like/{petVideoId}")
    public Response<Void> deletePetLikeCount(@PathVariable final int petVideoId) {
        shortFormService.deletePetVideoLikeCount(petVideoId);
        return Response.success();
    }
}
