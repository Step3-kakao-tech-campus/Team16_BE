package com.daggle.animory.domain.pet.service;


import com.daggle.animory.common.error.exception.UnAuthorized401;
import com.daggle.animory.domain.account.entity.Account;
import com.daggle.animory.domain.fileserver.FileRepository;
import com.daggle.animory.domain.pet.repository.PetPolygonRepository;
import com.daggle.animory.domain.pet.repository.PetRepository;
import com.daggle.animory.domain.pet.dto.request.PetRegisterRequestDto;
import com.daggle.animory.domain.pet.dto.request.PetUpdateRequestDto;
import com.daggle.animory.domain.pet.dto.response.*;
import com.daggle.animory.domain.pet.entity.Pet;
import com.daggle.animory.domain.shelter.ShelterRepository;
import com.daggle.animory.domain.shelter.entity.Shelter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class PetService {
    private final FileRepository fileRepository;
    private final ShelterRepository shelterRepository;
    private final PetRepository petRepository;
    private final PetPolygonRepository petPolygonRepository;

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

    public RegisterPetSuccessDto registerPet(final Account account, final PetRegisterRequestDto petRequestDTO, final MultipartFile image, final MultipartFile video) {
        // 이미지, 숏폼 파일 저장 후 url 얻어오기
        String imageUrl = fileRepository.storeFile(image);
        String videoUrl = fileRepository.storeFile(video);

        // 펫 등록을 요청한 유저의 보호소 조회
        Shelter shelter = shelterRepository.findByAccountId(account.getId())
                .orElseThrow(() -> new UnAuthorized401("보호소 정보가 존재하지 않습니다."));

        // 펫 DB 저장
        Pet registerPet = petRepository.save(petRequestDTO.toEntity(shelter, imageUrl, videoUrl));

        // 펫 다각형 프로필 DB 저장
        petPolygonRepository.save(petRequestDTO.petPolygonProfileDto().toEntity(registerPet));

        return new RegisterPetSuccessDto(registerPet.getId());
    }

    public UpdatePetSuccessDto updatePet(final Optional<PetUpdateRequestDto> petUpdateRequestDto, final Optional<MultipartFile> image, final Optional<MultipartFile> video) {
        throw new NotImplementedException("NotImplemented yet");
    }
}
