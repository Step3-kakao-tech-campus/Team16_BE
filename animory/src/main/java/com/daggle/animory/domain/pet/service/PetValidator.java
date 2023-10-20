package com.daggle.animory.domain.pet.service;

import com.daggle.animory.common.error.exception.Forbidden403;
import com.daggle.animory.common.error.exception.NotFound404;
import com.daggle.animory.domain.account.entity.Account;
import com.daggle.animory.domain.pet.entity.Pet;
import com.daggle.animory.domain.shelter.ShelterRepository;
import com.daggle.animory.domain.shelter.entity.Shelter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PetValidator {

    private final ShelterRepository shelterRepository;

    /** <pre>
     * 펫 수정 요청이 올바른지 검사합니다.
     *
     * 1. 데이터 무결성 검사
     * 요청한 계정의 보호소가 존재하는지 확인합니다.
     *
     * 2. 수정 권한 검사
     * 수정과 관련된 요청에서, 펫 수정 권한이 있는지 확인합니다.*
     * 요청받은 계정에 연결된 보호소가 같은지 확인합니다.
     * </pre>
     */
    public void validatePetUpdateAuthority(final String email,
                                           final Pet pet){

        // Data Integrity Validation
        final Shelter shelterFromRequest = shelterRepository.findByAccountEmail(email)
            .orElseThrow(() -> new NotFound404("보호소 정보가 존재하지 않습니다."));
        final Shelter shelterToUpdate = pet.getShelter();

        // Authorization Validation
        if(!shelterFromRequest.equals(shelterToUpdate)){
            throw new Forbidden403("펫 수정 권한이 없습니다.");
        }
    }
}
