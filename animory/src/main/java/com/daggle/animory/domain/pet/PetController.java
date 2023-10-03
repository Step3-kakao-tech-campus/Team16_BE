package com.daggle.animory.domain.pet;

import com.daggle.animory.common.Response;
import com.daggle.animory.domain.pet.dto.request.PetRegisterRequestDto;
import com.daggle.animory.domain.pet.dto.request.PetUpdateRequestDto;
import com.daggle.animory.domain.pet.dto.response.*;
import com.daggle.animory.domain.pet.service.PetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/pet")
public class PetController {

    private final PetService petService;

    @PostMapping(value = "", consumes = {"multipart/form-data"})
    public Response<RegisterPetSuccessDto> registerPet(
        @RequestPart(value = "petInfo") final PetRegisterRequestDto petRegisterRequestDto,
        @RequestPart(value = "profileImage") final MultipartFile image,
        @RequestPart(value = "profileVideo") final MultipartFile video
    ) {

        return Response.success(petService.registerPet(petRegisterRequestDto, image, video));
    }

    @PatchMapping(value = "", consumes = {"multipart/form-data"})
    public Response<UpdatePetSuccessDto> registerPet(
        @RequestPart(value = "petInfo") final Optional<PetUpdateRequestDto> petUpdateRequestDto,
        @RequestPart(value = "profileImage") final Optional<MultipartFile> image,
        @RequestPart(value = "profileVideo") final Optional<MultipartFile> video
    ) {

        return Response.success(petService.updatePet(petUpdateRequestDto, image, video));
    }


    /**
     * Pagination이 아닌, 각 8개씩 반환합니다.
     * 이후 더보기 버튼을 통해 다른 API를 호출되는 시나리오 입니다.
     */
    @GetMapping("/profiles")
    public Response<PetProfilesDto> getPetProfiles() {
        return Response.success(petService.getPetProfiles());
    }

    @GetMapping("/profiles/sos")
    public Response<SosPetProfilesDto> getPetSosProfiles(final Pageable pageable) {
        return Response.success(petService.getPetSosProfiles(pageable));
    }

    @GetMapping("/profiles/new")
    public Response<NewPetProfilesDto> getPetNewProfiles(final Pageable pageable) {
        return Response.success(petService.getPetNewProfiles(pageable));
    }

    @GetMapping("/{petId}")
    public Response<PetDto> getPetDetail(@PathVariable final int petId) {
        return Response.success(petService.getPetDetail(petId));
    }

    @GetMapping("/image")
    public ResponseEntity<Resource> getPetImage(@RequestParam("fileName") String fileName){
        Resource resource = petService.getPetImageByURL(fileName);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(
                ContentDisposition.builder("attachment")
                        .filename("fileName", StandardCharsets.UTF_8)
                        .build()
        );
        headers.add(HttpHeaders.CONTENT_TYPE,"image/jpg");

        return new ResponseEntity<Resource>(resource,headers, HttpStatus.OK);
    }
}
