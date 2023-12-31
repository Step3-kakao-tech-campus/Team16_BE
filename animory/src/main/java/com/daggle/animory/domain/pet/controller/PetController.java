package com.daggle.animory.domain.pet.controller;

import com.daggle.animory.common.Response;
import com.daggle.animory.common.security.UserDetailsImpl;
import com.daggle.animory.domain.pet.dto.request.PetRegisterRequestDto;
import com.daggle.animory.domain.pet.dto.request.PetUpdateRequestDto;
import com.daggle.animory.domain.pet.dto.response.*;
import com.daggle.animory.domain.pet.service.PetReadService;
import com.daggle.animory.domain.pet.service.PetWriteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    @PostMapping(value = "", consumes = {"multipart/form-data"})
    public Response<RegisterPetSuccessDto> registerPet(
        @AuthenticationPrincipal final UserDetailsImpl userDetails,
        @RequestPart(value = "petInfo") final PetRegisterRequestDto petRegisterRequestDto,
        @RequestPart(value = "profileImage") final MultipartFile image,
        @RequestPart(value = "profileVideo") final MultipartFile video
    ) {

        return Response.success(
            petWriteService.registerPet(userDetails, petRegisterRequestDto, image, video));
    }

    // Pet 수정 페이지에서, 기존 등록된 정보를 확인하기 위해 호출하는 API
    @GetMapping(value = "/register-info/{petId}")
    public Response<PetRegisterInfoDto> getPetRegisterInfo(@AuthenticationPrincipal final UserDetailsImpl userDetails,
                                                           @PathVariable final int petId) {

        return Response.success(
            petReadService.getRegisterInfo(userDetails, petId));
    }

    // Pet 수정 요청
    @PatchMapping(value = "/{petId}", consumes = {"multipart/form-data"})
    public Response<UpdatePetSuccessDto> updatePet(
        @AuthenticationPrincipal final UserDetailsImpl userDetails,
        @PathVariable final int petId,
        @RequestPart(value = "petInfo") final PetUpdateRequestDto petUpdateRequestDto,
        @RequestPart(value = "profileImage", required = false) final MultipartFile image,
        @RequestPart(value = "profileVideo", required = false) final MultipartFile video
    ) {

        return Response.success(
            petWriteService.updatePet(userDetails, petId, petUpdateRequestDto, image, video));
    }

    // Pagination이 아닌, 각 8개씩 반환합니다. 이후 더보기 버튼을 통해 다른 API를 호출되는 시나리오 입니다.
    @GetMapping("/profiles")
    public Response<PetProfilesDto> getPetProfiles() {
        return Response.success(petReadService.getPetProfiles());
    }

    @GetMapping("/profiles/sos")
    public Response<SosPetProfilesDto> getPetSosProfiles(
        @PageableDefault(size = 8) final Pageable pageable) {
        return Response.success(petReadService.getPetSosProfiles(pageable));
    }


    @GetMapping("/profiles/new")
    public Response<NewPetProfilesDto> getPetNewProfiles(
        @PageableDefault(size = 8, sort = "createdAt", direction = Direction.DESC) final Pageable pageable) {
        return Response.success(petReadService.getPetNewProfiles(pageable));
    }

    @GetMapping("/{petId}")
    public Response<PetDto> getPetDetail(@PathVariable final int petId) {
        return Response.success(petReadService.getPetDetail(petId));
    }

    // Pet 입양 완료 상태 등록
    @PostMapping("/adoption/{petId}")
    public Response<Void> updatePetAdopted(@AuthenticationPrincipal final UserDetailsImpl userDetails,
                                           @PathVariable final int petId) {
        petWriteService.updatePetAdopted(userDetails, petId);
        return Response.success();
    }


}
