package com.daggle.animory.domain.pet;

import com.daggle.animory.common.Response;
import com.daggle.animory.domain.pet.dto.response.NewPetProfilesDto;
import com.daggle.animory.domain.pet.dto.response.PetProfilesDto;
import com.daggle.animory.domain.pet.dto.response.SosPetProfilesDto;
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
    public Response<?> registerPet(
            @RequestPart(value = "key")String PetRequestDTO,
            @RequestPart(value = "image")MultipartFile image,
            @RequestPart(value = "video")MultipartFile video
            ){
        petFileStorageService.storeFile(image);
        petFileStorageService.storeFile(video);

        log.debug("PetRequestDTO: {}", PetRequestDTO);

        Response<?> apiResult = Response.success();
        return apiResult;
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


}
