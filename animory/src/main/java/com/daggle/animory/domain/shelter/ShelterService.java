package com.daggle.animory.domain.shelter;

import com.daggle.animory.common.error.exception.NotFound404;
import com.daggle.animory.domain.pet.PetRepository;
import com.daggle.animory.domain.shelter.dto.response.ShelterProfilePage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShelterService {
    private final ShelterRepository shelterRepository;
    private final PetRepository petRepository;

    public ShelterProfilePage getShelterProfile(Integer shelterId, int page) {
        return ShelterProfilePage.of(
                shelterRepository.findById(shelterId).orElseThrow(
                        () -> new NotFound404("해당하는 보호소가 존재하지 않습니다.")),
                petRepository.findByShelterId(shelterId, PageRequest.of(page, 10))
        );
    }
}
