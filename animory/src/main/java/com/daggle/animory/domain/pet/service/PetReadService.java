package com.daggle.animory.domain.pet.service;


import com.daggle.animory.common.error.exception.NotFound404;
import com.daggle.animory.domain.account.entity.Account;
import com.daggle.animory.domain.pet.dto.response.*;
import com.daggle.animory.domain.pet.entity.Pet;
import com.daggle.animory.domain.pet.entity.PetPolygonProfile;
import com.daggle.animory.domain.pet.repository.PetPolygonRepository;
import com.daggle.animory.domain.pet.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PetReadService {

    private final PetRepository petRepository;
    private final PetPolygonRepository petPolygonRepository;
    private final PetValidator petValidator;

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



    // 기존의 펫 등록 정보 조회
    public PetRegisterInfoDto getRegisterInfo(final Account account,
                                              final int petId) {


        // 펫 id로 Pet, PetPolygonProfile 얻어오기
        final Pet registerPet = petRepository.findById(petId)
            .orElseThrow(() -> new NotFound404("등록되지 않은 펫입니다."));

        petValidator.validatePetUpdateAuthority(account, registerPet);

        final PetPolygonProfile petPolygonProfile = petPolygonRepository.findByPetId(petId)
            .orElseThrow(() -> new NotFound404("등록된 다각형 프로필이 존재하지 않습니다."));

        // Pet -> PetRegisterInfoDto
        return PetRegisterInfoDto.fromEntity(registerPet, petPolygonProfile);
    }
}
