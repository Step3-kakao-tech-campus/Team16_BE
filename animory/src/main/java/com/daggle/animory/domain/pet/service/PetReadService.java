package com.daggle.animory.domain.pet.service;


import com.daggle.animory.common.security.UserDetailsImpl;
import com.daggle.animory.domain.pet.dto.response.*;
import com.daggle.animory.domain.pet.entity.Pet;
import com.daggle.animory.domain.pet.exception.PetNotFoundException;
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
    private final PetValidator petValidator;

    public PetProfilesDto getPetProfiles() {
        // sos, new 프로필 각각 최대 8개씩 조회
        final List<Pet> sosProfiles = petRepository.findProfilesWithProtectionExpirationDate(PageRequest.of(0, 4)).getContent();
        final List<Pet> newProfiles = petRepository.findProfilesWithCreatedAt(PageRequest.of(0, 4)).getContent();

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
        return SosPetProfilesDto.of(petRepository.findProfilesWithProtectionExpirationDate(pageable));
    }

    public NewPetProfilesDto getPetNewProfiles(final Pageable pageable) {
        return NewPetProfilesDto.of(petRepository.findProfilesWithCreatedAt(pageable));
    }

    public PetDto getPetDetail(final int petId) {
        // petId로 Pet, PetPolygonProfile 얻어오기
        final Pet pet = petRepository.findById(petId)
            .orElseThrow(PetNotFoundException::new);

        return PetDto.fromEntity(pet);
    }


    // 기존의 펫 등록 정보 조회
    public PetRegisterInfoDto getRegisterInfo(final UserDetailsImpl userDetails,
                                              final int petId) {

        // 펫 id로 Pet, PetPolygonProfile 얻어오기
        final Pet registerPet = petRepository.findById(petId)
            .orElseThrow(PetNotFoundException::new);

        petValidator.validatePetUpdateAuthority(userDetails.getEmail(), registerPet);

        // Pet -> PetRegisterInfoDto
        return PetRegisterInfoDto.fromEntity(registerPet);
    }
}
