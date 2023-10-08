package com.daggle.animory.domain.pet;

import com.daggle.animory.common.Response;
import com.daggle.animory.common.security.Authorized;
import com.daggle.animory.domain.account.entity.Account;
import com.daggle.animory.domain.account.entity.AccountRole;
import com.daggle.animory.domain.pet.dto.request.PetRegisterRequestDto;
import com.daggle.animory.domain.pet.dto.request.PetUpdateRequestDto;
import com.daggle.animory.domain.pet.dto.response.NewPetProfilesDto;
import com.daggle.animory.domain.pet.dto.response.PetDto;
import com.daggle.animory.domain.pet.dto.response.PetProfilesDto;
import com.daggle.animory.domain.pet.dto.response.PetRegisterInfoDto;
import com.daggle.animory.domain.pet.dto.response.RegisterPetSuccessDto;
import com.daggle.animory.domain.pet.dto.response.SosPetProfilesDto;
import com.daggle.animory.domain.pet.dto.response.UpdatePetSuccessDto;
import com.daggle.animory.domain.pet.service.PetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/pet")
public class PetController {

    private final PetService petService;

    @PostMapping(value = "", consumes = {"multipart/form-data"})
    @Authorized(AccountRole.SHELTER)
    public Response<RegisterPetSuccessDto> registerPet(
            final Account account,
            @RequestPart(value = "petInfo") final PetRegisterRequestDto petRegisterRequestDto,
            @RequestPart(value = "profileImage") final MultipartFile image,
            @RequestPart(value = "profileVideo") final MultipartFile video
    ) {
        return Response.success(
                petService.registerPet(account, petRegisterRequestDto, image, video));
    }

    @PatchMapping(value = "/{petId}", consumes = {"multipart/form-data"})
    public Response<UpdatePetSuccessDto> updatePet(
            @PathVariable final int petId,
            @RequestPart(value = "petInfo") final PetUpdateRequestDto petUpdateRequestDto,
            @RequestPart(value = "profileImage", required = false) final MultipartFile image,
            @RequestPart(value = "profileVideo", required = false) final MultipartFile video
    ) {

        return Response.success(
                petService.updatePet(petId, petUpdateRequestDto, image, video));
    }

    @GetMapping(value = "/register-info/{petId}")
    public Response<PetRegisterInfoDto> getPetRegisterInfo(@PathVariable final int petId) {

        return Response.success(
                petService.getRegisterInfo(petId));
    }

    /**
     * Pagination이 아닌, 각 8개씩 반환합니다. 이후 더보기 버튼을 통해 다른 API를 호출되는 시나리오 입니다.
     */
    @GetMapping("/profiles")
    public Response<PetProfilesDto> getPetProfiles() {
        return Response.success(petService.getPetProfiles());
    }

    @GetMapping("/profiles/sos")
    public Response<SosPetProfilesDto> getPetSosProfiles(
            @PageableDefault(page = 0, size = 8, sort = "protectionExpirationDate", direction = Sort.Direction.ASC) final Pageable pageable) {
        return Response.success(petService.getPetSosProfiles(pageable));
    }

    @GetMapping("/profiles/new")
    public Response<NewPetProfilesDto> getPetNewProfiles(
            @PageableDefault(page = 0, size = 8, sort = "createdAt", direction = Direction.DESC) final Pageable pageable) {
        return Response.success(petService.getPetNewProfiles(pageable));
    }

    @GetMapping("/{petId}")
    public Response<PetDto> getPetDetail(@PathVariable final int petId) {
        return Response.success(petService.getPetDetail(petId));
    }


}
