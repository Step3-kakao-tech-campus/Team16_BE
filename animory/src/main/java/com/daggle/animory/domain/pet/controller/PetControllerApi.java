package com.daggle.animory.domain.pet.controller;

import com.daggle.animory.common.Response;
import com.daggle.animory.common.security.UserDetailsImpl;
import com.daggle.animory.domain.pet.dto.request.PetRegisterRequestDto;
import com.daggle.animory.domain.pet.dto.request.PetUpdateRequestDto;
import com.daggle.animory.domain.pet.dto.response.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Pet", description = """
        Pet 등록, 수정 및 조회 관련 API
        최종수정시각: 2023-10-22 23:56
    """)
public interface PetControllerApi {

    @Operation(summary = "[로그인 필요: 보호소] Pet 등록 요청",
        description = "Pet 등록 요청 API 입니다. Swagger Test가 작동하지 않을 수 있습니다.(https://github.com/springdoc/springdoc-openapi/issues/820)",
        parameters = {
            @Parameter(
                in = ParameterIn.HEADER,
                name = "Authorization",
                description = "Bearer Token",
                required = true,
                schema = @Schema(type = "string", example = "Bearer Token")
            )
        }
    )
    Response<RegisterPetSuccessDto> registerPet(
        UserDetailsImpl userDetails,
        @RequestPart(value = "petInfo") PetRegisterRequestDto petRegisterRequestDto,
        @RequestPart(value = "profileImage") MultipartFile image,
        @RequestPart(value = "profileVideo") MultipartFile video
    );

    @Operation(summary = "Pet 수정 페이지 진입, 기존 펫 정보 확인",
        description = "Pet 수정 페이지에서, 기존 등록된 정보를 확인하기 위해 호출하는 API 입니다.")
    Response<PetRegisterInfoDto> getPetRegisterInfo(UserDetailsImpl userDetails,
                                                    @PathVariable int petId);

    // Pet 수정 요청
    @Operation(summary = "[로그인 필요: 보호소] Pet 수정 요청",
        description = "보호소 계정 권한이 필요합니다. Swagger Test가 작동하지 않을 수 있습니다.(https://github.com/springdoc/springdoc-openapi/issues/820)",
        parameters = {
            @Parameter(
                in = ParameterIn.HEADER,
                name = "Authorization",
                description = "Bearer Token",
                required = true,
                schema = @Schema(type = "string", example = "Bearer Token")
            )
        }
    )
    Response<UpdatePetSuccessDto> updatePet(
        UserDetailsImpl userDetails,
        @PathVariable int petId,
        @RequestPart(value = "petInfo") PetUpdateRequestDto petUpdateRequestDto,
        @RequestPart(value = "profileImage", required = false) MultipartFile image,
        @RequestPart(value = "profileVideo", required = false) MultipartFile video
    );

    @Operation(summary = "Pet 프로필 목록 조회",
        description = "Pet 프로필 조회 API 입니다. Pagination이 아닌, 각 8개씩 반환합니다. 이후 더보기 버튼을 통해 다른 API를 호출되는 시나리오 입니다.")
    Response<PetProfilesDto> getPetProfiles();

    @Operation(summary = "Pet SOS 목록 조회",
        description = "긴급 도움이 필요한 Pet 목록 입니다.",
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
                schema = @Schema(type = "integer", defaultValue = "8")
            )
        }
    )
    Response<SosPetProfilesDto> getPetSosProfiles(
        @Parameter(hidden = true)
        @PageableDefault(page = 1, size = 8, sort = "protectionExpirationDate", direction = Sort.Direction.ASC) Pageable pageable);

    @Operation(summary = "Pet New 목록 조회",
        description = "신규 등록 Pet 목록 입니다.",
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
                schema = @Schema(type = "integer", defaultValue = "8")
            )
        }
    )
    Response<NewPetProfilesDto> getPetNewProfiles(
        @Parameter(hidden = true)
        @PageableDefault(page = 1, size = 8, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable);

    @Operation(summary = "Pet 상세 조회",
        description = "")
    Response<PetDto> getPetDetail(@PathVariable int petId);


    @Operation(summary = "[로그인 필요: 보호소] Pet 입양 완료 처리",
        description = "입양 상태가 변경되고, 보호만료날짜가 삭제됩니다.",
        parameters = {
            @Parameter(
                in = ParameterIn.HEADER,
                name = "Authorization",
                description = "Bearer Token",
                required = true,
                schema = @Schema(type = "string", example = "Bearer Token")
            )
        }
    )
    Response<Void> updatePetAdopted(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                    @PathVariable int petId);
}
