package com.daggle.animory.domain.pet.controller;

import com.daggle.animory.common.Response;
import com.daggle.animory.domain.account.entity.Account;
import com.daggle.animory.domain.pet.dto.request.PetRegisterRequestDto;
import com.daggle.animory.domain.pet.dto.request.PetUpdateRequestDto;
import com.daggle.animory.domain.pet.dto.response.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Pet", description = "Pet 등록, 수정 및 조회 관련 API")
public interface PetControllerApi {

    @Operation(summary = "Pet 등록 요청",
        description = "Pet 등록 요청 API 입니다. 보호소 계정 권한이 필요합니다.")
    Response<RegisterPetSuccessDto> registerPet(
        Account account,
        @RequestPart(value = "petInfo") PetRegisterRequestDto petRegisterRequestDto,
        @RequestPart(value = "profileImage") MultipartFile image,
        @RequestPart(value = "profileVideo") MultipartFile video
    );

    @Operation(summary = "Pet 수정 페이지 진입, 기존 펫 정보 확인",
        description = "Pet 수정 페이지에서, 기존 등록된 정보를 확인하기 위해 호출하는 API 입니다. 보호소 계정 권한이 필요합니다.")
    Response<PetRegisterInfoDto> getPetRegisterInfo(Account account,
                                                    @PathVariable int petId);

    // Pet 수정 요청
    @Operation(summary = "Pet 수정 요청",
        description = "보호소 계정 권한이 필요합니다.")
    Response<UpdatePetSuccessDto> updatePet(
        Account account,
        @PathVariable int petId,
        @RequestPart(value = "petInfo") PetUpdateRequestDto petUpdateRequestDto,
        @RequestPart(value = "profileImage", required = false) MultipartFile image,
        @RequestPart(value = "profileVideo", required = false) MultipartFile video
    );

    @Operation(summary = "Pet 프로필 목록 조회",
        description = "Pet 프로필 조회 API 입니다. Pagination이 아닌, 각 8개씩 반환합니다. 이후 더보기 버튼을 통해 다른 API를 호출되는 시나리오 입니다.")
    Response<PetProfilesDto> getPetProfiles();

    @Operation(summary = "Pet SOS 목록 조회",
        description = "긴급 도움이 필요한 Pet 목록 입니다. page, size의 기본값은 0, 8 입니다.")
    Response<SosPetProfilesDto> getPetSosProfiles(
        @PageableDefault(page = 0, size = 8, sort = "protectionExpirationDate", direction = Sort.Direction.ASC) Pageable pageable);

    @Operation(summary = "Pet New 목록 조회",
        description = "신규 등록 Pet 목록 입니다. page, size의 기본값은 0, 8 입니다.")
    Response<NewPetProfilesDto> getPetNewProfiles(
        @PageableDefault(page = 0, size = 8, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable);

    @Operation(summary = "Pet 상세 조회",
        description = "")
    Response<PetDto> getPetDetail(@PathVariable int petId);
}
