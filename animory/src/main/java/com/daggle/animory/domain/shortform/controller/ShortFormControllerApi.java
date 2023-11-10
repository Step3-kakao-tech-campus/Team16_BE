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
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Tag(name = "숏폼 API", description = """
        최종수정일: 2023-11-06
    """)
public interface ShortFormControllerApi {

    @Operation(summary = "조건부 숏폼 비디오 탐색",
        description = "다양한 검색 조건 변화에 대응할 수 있는 목적으로 사용할 숏폼 비디오 조회 API 입니다.",
        parameters = {
            @Parameter(
                in = ParameterIn.QUERY,
                name = "type",
                description = "Pet 종류",
                schema = @Schema(implementation = PetType.class)
            ),
            @Parameter(
                in = ParameterIn.QUERY,
                name = "area",
                description = "지역",
                schema = @Schema(implementation = Province.class)
            ),
            @Parameter(
                in = ParameterIn.QUERY,
                name = "page",
                description = "페이지 번호",
                schema = @Schema(type = "integer", defaultValue = "1")
            ),
            @Parameter(
                in = ParameterIn.QUERY,
                name = "size",
                description = "페이지 크기",
                allowEmptyValue = true,
                schema = @Schema(type = "integer", defaultValue = "10")
            )
        }
    )
    @GetMapping("/short-forms")
    Response<CategoryShortFormPage> getShortForms(@Parameter(hidden = true)
                                                  @ModelAttribute @Valid ShortFormSearchCondition searchCondition,
                                                  @Parameter(hidden = true) // 문서에 깔끔하게 나오지 않을 경우, 이런 식으로 그냥 숨겨버립니다.
                                                  @PageableDefault Pageable pageable);

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
                description = "페이지 크기",
                allowEmptyValue = true,
                schema = @Schema(type = "integer", defaultValue = "10")
            )
        }
    )
    @GetMapping("/short-forms/home")
    Response<HomeShortFormPage> getHomeShortForms(@Parameter(hidden = true) @PageableDefault Pageable pageable);

    @Operation(summary = "숏폼 좋아요 등록",
            description = "특정 숏폼에 좋아요를 등록합니다.",
            parameters = {
                @Parameter(
                    in = ParameterIn.HEADER,
                    name = "X-Forwarded-For",
                    description = "클라이언트 IP",
                    required = true,
                    schema = @Schema(type = "string", defaultValue = "")
            )
        }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "좋아요 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "헤당하는 Ip로 숏폼에 좋아요가 이미 등록되있는 경우", content = @Content),
            @ApiResponse(responseCode = "404", description = "해당하는 숏폼이 존재하지 않을 경우", content = @Content)
    })
    @PostMapping("/like/{petVideoId}")
    Response<Void> increasePetLikeCount(final HttpServletRequest httpServletRequest,
                                        @PathVariable final int petVideoId);
    @Operation(summary = "숏폼 좋아요 삭제",
            description = "특정 숏폼에 좋아요를 삭제합니다.",
            parameters = {
                    @Parameter(
                            in = ParameterIn.HEADER,
                            name = "X-Forwarded-For",
                            description = "클라이언트 IP",
                            required = true,
                            schema = @Schema(type = "string", defaultValue = "")
                    )
            }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "좋아요 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "헤당하는 Ip로 숏폼에 좋아요가 등록되있지 않은 경우", content = @Content),
            @ApiResponse(responseCode = "404", description = "해당하는 숏폼이 존재하지 않을 경우", content = @Content)
    })
    @DeleteMapping("/like/{petVideoId}")
    Response<Void> deletePetLikeCount(final HttpServletRequest httpServletRequest, @PathVariable final int petVideoId);
}
