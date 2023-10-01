package com.daggle.animory.domain.pet;

import com.daggle.animory.common.Response;
import com.daggle.animory.domain.pet.dto.request.PetRegisterRequestDto;
import com.daggle.animory.domain.pet.dto.response.*;
import com.daggle.animory.domain.pet.fileIO.PetFileStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/pet")
@RequiredArgsConstructor
public class PetController {

    private final PetService petService;
    private final PetFileStorageService petFileStorageService;


    @PostMapping(value = "", consumes = {"multipart/form-data"})
    public Response<RegisterPetSuccessDto> registerPet(
            @RequestPart(value = "petInfo") final PetRegisterRequestDto petRegisterRequestDto,
            @RequestPart(value = "profileImage") final MultipartFile image,
            @RequestPart(value = "profileVideo") final MultipartFile video
    ){

        return Response.success(petService.registerPet(petRegisterRequestDto, image, video));
    }



    /**
     * Pagination이 아닌, 각 8개씩 반환합니다.
     * 이후 더보기 버튼을 통해 다른 API를 호출되는 시나리오 입니다.
     */
    @GetMapping("/profiles")
    public Response<PetProfilesDto> getPetProfiles(){
        return Response.success(petService.getPetProfiles());
    }

    @GetMapping("/profiles/sos")
    public Response<SosPetProfilesDto> getPetSosProfiles(final Pageable pageable){
        return Response.success(petService.getPetSosProfiles(pageable));
    }

    @GetMapping("/profiles/new")
    public Response<NewPetProfilesDto> getPetNewProfiles(final Pageable pageable){
        return Response.success(petService.getPetNewProfiles(pageable));
    }

    @GetMapping("/{petId}")
    public Response<PetDto> getPetDetail(@PathVariable final int petId){
        return Response.success(petService.getPetDetail(petId));
    }


}
