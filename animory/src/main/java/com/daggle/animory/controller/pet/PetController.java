package com.daggle.animory.controller.pet;

import com.daggle.animory.common.Response;
import com.daggle.animory.service.pet.PetService;
import com.daggle.animory.service.pet.fileIO.PetFileStorageService;
import com.daggle.animory.service.pet.fileIO.PetFileStorageServiceWithoutSaving;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
public class PetController {

    private final PetService petService;
    private final PetFileStorageService petFileStorageService;
    private final PetFileStorageServiceWithoutSaving petFileStorageServiceWithoutSaving;

    @PostMapping("/pet")
    public Response<?> registerPet(
            @RequestPart(value = "key")String PetRequestDTO,
            @RequestPart(value = "image")MultipartFile image,
            @RequestPart(value = "video")MultipartFile video
            ){
        petFileStorageService.storeFile(image);
        petFileStorageService.storeFile(video);
        System.out.println(PetRequestDTO);
        Response<?> apiResult = Response.success();
        return apiResult;
    }
    @PostMapping("/pet-test")
    public Response<?> registerPetWithoutSaving(
            @RequestPart(value = "key")String PetRequestDTO,
            @RequestPart(value = "image")MultipartFile image,
            @RequestPart(value = "video")MultipartFile video
    ){
        petFileStorageServiceWithoutSaving.storeFile(image);
        petFileStorageServiceWithoutSaving.storeFile(video);
        System.out.println(PetRequestDTO);
        Response<?> apiResult = Response.success();
        return apiResult;
    }
}
