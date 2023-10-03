package com.daggle.animory.domain.pet.service;


import com.daggle.animory.common.error.exception.BadRequest400;
import com.daggle.animory.domain.pet.dto.request.PetRegisterRequestDto;
import com.daggle.animory.domain.pet.dto.request.PetUpdateRequestDto;
import com.daggle.animory.domain.pet.dto.response.*;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Optional;


@Service
public class PetService {

    private final String UPLOAD_DIR = ".\\src\\main\\resources\\temporaryImage\\";
    public PetProfilesDto getPetProfiles() {
        throw new NotImplementedException("NotImplemented yet");
    }

    public SosPetProfilesDto getPetSosProfiles(final Pageable pageable) {
        throw new NotImplementedException("NotImplemented yet");
    }

    public NewPetProfilesDto getPetNewProfiles(final Pageable pageable) {
        throw new NotImplementedException("NotImplemented yet");
    }

    public PetDto getPetDetail(final int petId) {
        throw new NotImplementedException("NotImplemented yet");
    }

    public RegisterPetSuccessDto registerPet(final PetRegisterRequestDto petRequestDTO, final MultipartFile image, final MultipartFile video) {
        throw new NotImplementedException("NotImplemented yet");
    }

    public UpdatePetSuccessDto updatePet(final Optional<PetUpdateRequestDto> petUpdateRequestDto, final Optional<MultipartFile> image, final Optional<MultipartFile> video) {
        throw new NotImplementedException("NotImplemented yet");
    }

    public Resource getPetImageByURL(final String fileName){
        String url = UPLOAD_DIR + fileName;
        try{
            return new InputStreamResource(new FileInputStream(url));
        }catch(FileNotFoundException ex){
            throw new BadRequest400("해당 파일을 찾을 수 없습니다.");
        }
    }
}
