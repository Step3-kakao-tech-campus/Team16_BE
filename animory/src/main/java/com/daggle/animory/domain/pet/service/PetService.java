package com.daggle.animory.domain.pet.service;


import com.daggle.animory.common.error.exception.NotFound404;
import com.daggle.animory.common.error.exception.UnAuthorized401;
import com.daggle.animory.domain.account.entity.Account;
import com.daggle.animory.domain.fileserver.S3FileRepository;
import com.daggle.animory.domain.pet.dto.request.PetRegisterRequestDto;
import com.daggle.animory.domain.pet.dto.request.PetUpdateRequestDto;
import com.daggle.animory.domain.pet.dto.response.*;
import com.daggle.animory.domain.pet.entity.Pet;
import com.daggle.animory.domain.pet.entity.PetPolygonProfile;
import com.daggle.animory.domain.pet.repository.PetPolygonRepository;
import com.daggle.animory.domain.pet.repository.PetRepository;
import com.daggle.animory.domain.shelter.ShelterRepository;
import com.daggle.animory.domain.shelter.entity.Shelter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PetService {

    private final S3FileRepository fileRepository;
    private final ShelterRepository shelterRepository;
    private final PetRepository petRepository;
    private final PetPolygonRepository petPolygonRepository;

    public PetProfilesDto getPetProfiles() {
        // sos, new 프로필 각각 최대 8개씩 조회
        final List<Pet> sosProfiles = petRepository.findProfilesWithProtectionExpirationDate(PageRequest.of(0, 8));
        final List<Pet> newProfiles = petRepository.findProfilesWithCreatedAt(PageRequest.of(0, 8));

        // DTO에 넣어주기
        final List<SosPetDto> sosList = sosProfiles.stream()
                .map(SosPetDto::fromEntity)
                .toList();

        final List<NewPetDto> newList = newProfiles.stream()
                .map(NewPetDto::fromEntity)
                .toList();

        return new PetProfilesDto(sosList, newList);
    }

    public SosPetProfilesDto getPetSosProfiles(final Pageable pageable) {
        return SosPetProfilesDto.of(petRepository.findPageBy(pageable));
    }

    public NewPetProfilesDto getPetNewProfiles(final Pageable pageable) {
        return NewPetProfilesDto.of(petRepository.findPageBy(pageable));
    }

    public PetDto getPetDetail(final int petId) {
        // petId로 Pet, PetPolygonProfile 얻어오기
        final Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new NotFound404("해당 동물이 존재하지 않습니다."));

        final PetPolygonProfile petPolygonProfile = petPolygonRepository.findByPetId(petId)
                .orElseThrow(() -> new NotFound404("등록된 다각형 프로필이 존재하지 않습니다."));

        return PetDto.fromEntity(pet, petPolygonProfile);
    }

    @Transactional
    public RegisterPetSuccessDto registerPet(final Account account,
            final PetRegisterRequestDto petRequestDTO, final MultipartFile image,
            final MultipartFile video) {

        // 이미지, 숏폼 파일 저장 후 url 얻어오기
        final URL imageUrl = fileRepository.save(image);
        final URL videoUrl = fileRepository.save(video);

        // 펫 등록을 요청한 유저의 보호소 조회
        final Shelter shelter = shelterRepository.findByAccountId(account.getId())
                .orElseThrow(() -> new UnAuthorized401("보호소 정보가 존재하지 않습니다."));

        // 펫 DB 저장
        final Pet registerPet = petRepository.save(
                petRequestDTO.toEntity(shelter, imageUrl.toString(), videoUrl.toString()));

        // 펫 다각형 프로필 DB 저장
        petPolygonRepository.save(petRequestDTO.petPolygonProfileDto().toEntity(registerPet));

        return new RegisterPetSuccessDto(registerPet.getId());
    }

    @Transactional
    public UpdatePetSuccessDto updatePet(final int petId,
            final PetUpdateRequestDto petUpdateRequestDto, final MultipartFile image,
            final MultipartFile video) {

        // 펫 id로 Pet, PetPolygonProfile 얻어오기
        final Pet updatePet = petRepository.findById(petId)
                .orElseThrow(() -> new NotFound404("등록되지 않은 펫입니다."));

        final PetPolygonProfile petPolygonProfile = petPolygonRepository.findByPetId(petId)
                .orElseThrow(() -> new NotFound404("등록된 다각형 프로필이 존재하지 않습니다."));

        // 이미지, 비디오 파일 업데이트
        updateFile(updatePet, image, video);

        // 펫 정보 업데이트
        updatePet.updateInfo(petUpdateRequestDto);
        petPolygonProfile.update(petUpdateRequestDto.petPolygonProfileDto());

        return new UpdatePetSuccessDto(updatePet.getId());
    }

    // 이미지, 비디오 파일 수정 요청 시 기존 파일 삭제 후 업데이트
    public void updateFile(final Pet updatePet, final MultipartFile image,
            final MultipartFile video) {
        if (image != null) {
            final URL imageUrl = fileRepository.save(image);
            updatePet.updateImage(imageUrl.toString());
        }
        if (video != null) {
            final URL videoUrl = fileRepository.save(video);
            updatePet.updateVideo(videoUrl.toString());
        }
    }

    // 기존의 펫 등록 정보 조회
    public PetRegisterInfoDto getRegisterInfo(final int petId) {

        // 펫 id로 Pet, PetPolygonProfile 얻어오기
        final Pet registerPet = petRepository.findById(petId)
                .orElseThrow(() -> new NotFound404("등록되지 않은 펫입니다."));

        final PetPolygonProfile petPolygonProfile = petPolygonRepository.findByPetId(petId)
                .orElseThrow(() -> new NotFound404("등록된 다각형 프로필이 존재하지 않습니다."));

        // Pet -> PetRegisterInfoDto
        return PetRegisterInfoDto.fromEntity(registerPet, petPolygonProfile);
    }
}
