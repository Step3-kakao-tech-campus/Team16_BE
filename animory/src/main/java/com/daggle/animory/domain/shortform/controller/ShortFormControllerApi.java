package com.daggle.animory.domain.shortform.controller;

import com.daggle.animory.common.Response;
import com.daggle.animory.domain.pet.entity.PetType;
import com.daggle.animory.domain.shelter.entity.Province;
import com.daggle.animory.domain.shortform.dto.request.ShortFormSearchCondition;
import com.daggle.animory.domain.shortform.dto.response.CategoryShortFormPage;
import com.daggle.animory.domain.shortform.dto.response.HomeShortFormPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.validation.Valid;

@Tag(
    name = "숏폼 비디오 API",
    description = """
        최종수정시각: 2023-10-22 23:24
    """
)
public interface ShortFormControllerApi {

    @Operation(summary = "조건부 숏폼 비디오 탐색",
        description = "다양한 검색 조건 변화에 대응할 수 있는 목적으로 사용할 숏폼 비디오 조회 API 입니다.",
        parameters = {
            @Parameter(
                in = ParameterIn.QUERY,
                name = "type",
                description = "Pet 종류",
                required = true,
                schema = @Schema(implementation = PetType.class)
            ),
            @Parameter(
                in = ParameterIn.QUERY,
                name = "area",
                description = "지역",
                required = true,
                schema = @Schema(implementation = Province.class)
            ),
            @Parameter(
                in = ParameterIn.QUERY,
                name = "page",
                description = "페이지 번호",
                required = true,
                schema = @Schema(type = "integer", defaultValue = "1")
            ),
            @Parameter(
                in = ParameterIn.QUERY,
                name = "size",
                description = "페이지 크기, 기본 값은 10입니다.",
                allowEmptyValue = true,
                schema = @Schema(type = "integer")
            )
        }
    )
    @GetMapping("/short-forms")
    Response<CategoryShortFormPage> getShortForms(@Parameter(hidden = true)
                                                  @ModelAttribute @Valid ShortFormSearchCondition searchCondition,
                                                  @Parameter(hidden = true) // 문서에 깔끔하게 나오지 않을 경우, 이런 식으로 그냥 숨겨버립니다.
                                                  @PageableDefault(page = 1, size = 10) Pageable pageable);

    @Operation(summary = "홈 화면 숏폼 비디오 조회",
        description = "홈 화면에서 보여 줄 숏폼 비디오들을 조회합니다.",
        parameters = {
            @Parameter(
                in = ParameterIn.QUERY,
                name = "page",
                description = "페이지 번호",
                required = true,
                schema = @Schema(type = "integer", defaultValue = "1")
            ),
            @Parameter(
                in = ParameterIn.QUERY,
                name = "size",
                description = "페이지 크기, 기본 값은 10입니다.",
                allowEmptyValue = true,
                schema = @Schema(type = "integer")
            )
        }
    )
    @GetMapping("/short-forms/home")
    Response<HomeShortFormPage> getHomeShortForms(@PageableDefault(page = 1, size = 10) Pageable pageable);

}
