package com.daggle.animory.domain.shelter;

import com.daggle.animory.common.error.exception.Forbidden403;
import com.daggle.animory.common.error.exception.NotFound404;
import com.daggle.animory.common.security.UserDetailsImpl;
import com.daggle.animory.domain.pet.repository.PetRepository;
import com.daggle.animory.domain.shelter.dto.request.ShelterUpdateDto;
import com.daggle.animory.domain.shelter.dto.response.ShelterLocationDto;
import com.daggle.animory.domain.shelter.dto.response.ShelterProfilePage;
import com.daggle.animory.domain.shelter.dto.response.ShelterUpdateSuccessDto;
import com.daggle.animory.domain.shelter.entity.Shelter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShelterService {
    private final ShelterRepository shelterRepository;
    private final PetRepository petRepository;

    public ShelterProfilePage getShelterProfile(final Integer shelterId,
                                                final int page) {
        return ShelterProfilePage.of(
                shelterRepository.findById(shelterId).orElseThrow(
                        () -> new NotFound404("해당하는 보호소가 존재하지 않습니다.")),
                petRepository.findByShelterId(shelterId, PageRequest.of(page, 10))
        );
    }

    public List<ShelterLocationDto> filterExistShelterListByLocationId(final List<Integer> shelterLocationIdList) {
        return shelterRepository.findAllByKakaoLocationIdIn(shelterLocationIdList)
                .stream()
                .map(ShelterLocationDto::of)
                .toList();
    }

    @Transactional
    public ShelterUpdateSuccessDto updateShelterInfo(UserDetailsImpl userDetails, Integer shelterId, ShelterUpdateDto shelterUpdateDto) {
        Shelter shelter = shelterRepository.findById(shelterId).orElseThrow(
                () -> new NotFound404("해당하는 보호소가 존재하지 않습니다.")
        );

        if (!shelter.getAccount().getEmail().equals(userDetails.getUsername())) {
            throw new Forbidden403("보호소 정보를 수정할 권한이 없습니다.");
        }

        shelter.updateInfo(shelterUpdateDto);

        return ShelterUpdateSuccessDto.builder()
                .shelterId(shelterId)
                .build();
    }
}
