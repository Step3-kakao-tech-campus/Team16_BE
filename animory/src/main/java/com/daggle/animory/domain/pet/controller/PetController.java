package com.daggle.animory.domain.pet.controller;

import com.daggle.animory.common.Response;
import com.daggle.animory.common.security.RequireRole;
import com.daggle.animory.domain.account.entity.Account;
import com.daggle.animory.domain.account.entity.AccountRole;
import com.daggle.animory.domain.pet.dto.request.PetRegisterRequestDto;
import com.daggle.animory.domain.pet.dto.request.PetUpdateRequestDto;
import com.daggle.animory.domain.pet.dto.response.*;
import com.daggle.animory.domain.pet.service.PetReadService;
import com.daggle.animory.domain.pet.service.PetWriteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/pet")
public class PetController implements PetControllerApi {

    private final PetReadService petReadService;
    private final PetWriteService petWriteService;

    // Pet 등록
    @RequireRole(AccountRole.SHELTER)
    @PostMapping(value = "", consumes = {"multipart/form-data"})
    public Response<RegisterPetSuccessDto> registerPet(
        final Account account,
        @RequestPart(value = "petInfo") final PetRegisterRequestDto petRegisterRequestDto,
        @RequestPart(value = "profileImage") final MultipartFile image,
        @RequestPart(value = "profileVideo") final MultipartFile video
    ) {

        return Response.success(
            petWriteService.registerPet(account, petRegisterRequestDto, image, video));
    }

    // Pet 수정 페이지에서, 기존 등록된 정보를 확인하기 위해 호출하는 API
    @RequireRole(AccountRole.SHELTER)
    @GetMapping(value = "/register-info/{petId}")
    public Response<PetRegisterInfoDto> getPetRegisterInfo(final Account account,
                                                           @PathVariable final int petId) {

        return Response.success(
            petReadService.getRegisterInfo(account, petId));
    }

    // Pet 수정 요청
    @RequireRole(AccountRole.SHELTER)
    @PatchMapping(value = "/{petId}", consumes = {"multipart/form-data"})
    public Response<UpdatePetSuccessDto> updatePet(
        final Account account,
        @PathVariable final int petId,
        @RequestPart(value = "petInfo") final PetUpdateRequestDto petUpdateRequestDto,
        @RequestPart(value = "profileImage", required = false) final MultipartFile image,
        @RequestPart(value = "profileVideo", required = false) final MultipartFile video
    ) {

        return Response.success(
            petWriteService.updatePet(account, petId, petUpdateRequestDto, image, video));
    }


    // Pagination이 아닌, 각 8개씩 반환합니다. 이후 더보기 버튼을 통해 다른 API를 호출되는 시나리오 입니다.
    @GetMapping("/profiles")
    public Response<PetProfilesDto> getPetProfiles() {
        return Response.success(petReadService.getPetProfiles());
    }

    @GetMapping("/profiles/sos")
    public Response<SosPetProfilesDto> getPetSosProfiles(
        @PageableDefault(page = 0, size = 8, sort = "protectionExpirationDate", direction = Sort.Direction.ASC) final Pageable pageable) {
        return Response.success(petReadService.getPetSosProfiles(pageable));
    }

    @GetMapping("/profiles/new")
    public Response<NewPetProfilesDto> getPetNewProfiles(
        @PageableDefault(page = 0, size = 8, sort = "createdAt", direction = Direction.DESC) final Pageable pageable) {
        return Response.success(petReadService.getPetNewProfiles(pageable));
    }

    @GetMapping("/{petId}")
    public Response<PetDto> getPetDetail(@PathVariable final int petId) {
        return Response.success(petReadService.getPetDetail(petId));
    }

    // Pet 입양 완료 상태 등록
    @PostMapping("/adoption/{petId}")
    @RequireRole(AccountRole.SHELTER)
    public Response<Void> updatePetAdopted(final Account account,
                                           @PathVariable final int petId) {
        petWriteService.updatePetAdopted(account, petId);
        return Response.success();
    }


}
