package com.daggle.animory.domain.shelter.controller;

import com.daggle.animory.common.Response;
import com.daggle.animory.common.security.UserDetailsImpl;
import com.daggle.animory.domain.shelter.dto.request.ShelterUpdateDto;
import com.daggle.animory.domain.shelter.dto.response.ShelterLocationDto;
import com.daggle.animory.domain.shelter.dto.response.ShelterProfilePage;
import com.daggle.animory.domain.shelter.dto.response.ShelterUpdateSuccessDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;

@Tag(name = "보호소 API", description = """
        최종수정시각: 2023-10-22 23:44
    """)
public interface ShelterControllerApi {

    @Operation(summary = "보호소 프로필 조회", description = "보호소 프로필을 조회합니다.",
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
    @GetMapping("/{shelterId}")
    Response<ShelterProfilePage> getShelter(@PathVariable @Min(0) Integer shelterId,

                                            @Parameter(hidden = true)
                                            Pageable pageable);

    @Operation(summary = "[로그인 필요: 보호소] 보호소 정보 수정", description = "보호소 정보를 수정합니다.",
        parameters = {
            @Parameter(
                in = ParameterIn.HEADER,
                name = "Authorization",
                description = "Bearer Token",
                required = true,
                schema = @Schema(type = "string")
            ),
            @Parameter(
                in = ParameterIn.PATH,
                name = "shelterId",
                description = "보호소 ID",
                required = true,
                schema = @Schema(type = "integer", minimum = "0")
            ),
        },
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "보호소 정보 수정 요청",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ShelterUpdateDto.class)
            )
        )
    )
    @PutMapping("/{shelterId}")
    Response<ShelterUpdateSuccessDto> updateShelter(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                    @PathVariable @Min(0) Integer shelterId,
                                                    @RequestBody ShelterUpdateDto shelterUpdateDto);

    @Operation(summary = "등록된 보호소 필터링", description = "검색된 보호소의 KakaoLocationID 배열을 입력받아서, DB에서 KakaoLocationId가 일치하는" +
        " 보호소 정보를 목록으로 반환합니다.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "서비스 등록 여부를 확인하고자 하는 보호소들의 KakaoLocation ID 배열",
            required = true,
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    value = "[10240321, 20192232, 21293650]"
                )
            )
        )
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "회원가입 성공"),
        @ApiResponse(responseCode = "403", description = "내 보호소가 아닌 다른 보호소를 수정하려는 경우 권한이 없다.", content = @Content)
    })
    @PostMapping("/filter")
    Response<List<ShelterLocationDto>> filterExistShelterListByLocationId(@RequestBody List<Integer> shelterLocationIdList);
}
