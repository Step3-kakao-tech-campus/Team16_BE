package com.daggle.animory.domain.shortform.controller;

import com.daggle.animory.common.Response;
import com.daggle.animory.domain.shortform.dto.request.ShortFormSearchCondition;
import com.daggle.animory.domain.shortform.dto.response.CategoryShortFormPage;
import com.daggle.animory.domain.shortform.dto.response.HomeShortFormPage;
import com.daggle.animory.domain.shortform.service.PetVideoLikeService;
import com.daggle.animory.domain.shortform.service.ShortFormService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Validated
@RestController
@RequiredArgsConstructor
public class ShortFormController implements ShortFormControllerApi {

    private final ShortFormService shortFormService;
    private final PetVideoLikeService petVideoLikeService;

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
     * <p>
     * 검색 조건이 담기지 않은 /short-forms API와 동작이 동일하지만,
     * "서비스 첫 진입 시 홈 화면"에서 보여질 영상들의 기준이 달라질 수 있음을 고려하여 분리된 API를 유지합니다.
     */
    @GetMapping("/short-forms/home")
    public Response<HomeShortFormPage> getHomeShortForms(@PageableDefault final Pageable pageable) {
        return Response.success(shortFormService.getHomeShortFormPage(pageable));
    }

    @PostMapping("/like/{petVideoId}")
    public Response<Void> increasePetLikeCount(final HttpServletRequest httpServletRequest,
                                               @PathVariable final int petVideoId) {
        String ipAddress = httpServletRequest.getRemoteAddr();
        petVideoLikeService.updatePetVideoLikeCount(ipAddress, petVideoId);
        return Response.success();
    }

    @DeleteMapping("/like/{petVideoId}")
    public Response<Void> deletePetLikeCount(final HttpServletRequest httpServletRequest,
                                             @PathVariable final int petVideoId) {
        String ipAddress = httpServletRequest.getRemoteAddr();
        petVideoLikeService.deletePetVideoLikeCount(ipAddress, petVideoId);
        return Response.success();
    }
}
