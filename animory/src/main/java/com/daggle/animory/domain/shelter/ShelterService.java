package com.daggle.animory.domain.shelter;

import com.daggle.animory.common.security.UserDetailsImpl;
import com.daggle.animory.domain.pet.repository.PetRepository;
import com.daggle.animory.domain.shelter.dto.request.ShelterUpdateDto;
import com.daggle.animory.domain.shelter.dto.response.ShelterLocationDto;
import com.daggle.animory.domain.shelter.dto.response.ShelterProfilePage;
import com.daggle.animory.domain.shelter.dto.response.ShelterUpdateSuccessDto;
import com.daggle.animory.domain.shelter.entity.Shelter;
import com.daggle.animory.domain.shelter.exception.ShelterNotFoundException;
import com.daggle.animory.domain.shelter.exception.ShelterPermissionDeniedException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShelterService {
    private final ShelterRepository shelterRepository;
    private final PetRepository petRepository;

    public ShelterProfilePage getShelterProfile(final Integer shelterId,
                                                final Pageable pageable) {
        return ShelterProfilePage.of(
            shelterRepository.findById(shelterId).orElseThrow(
                ShelterNotFoundException::new),
            petRepository.findByShelterId(shelterId, pageable)
        );
    }

    public List<ShelterLocationDto> filterExistShelterListByLocationId(final List<Integer> shelterLocationIdList) {
        return shelterRepository.findAllByKakaoLocationIdIn(shelterLocationIdList)
            .stream()
            .map(ShelterLocationDto::of)
            .toList();
    }

    @Transactional
    public ShelterUpdateSuccessDto updateShelterInfo(final UserDetailsImpl userDetails,
                                                     final Integer shelterId,
                                                     final ShelterUpdateDto shelterUpdateDto) {
        final Shelter shelter = shelterRepository.findById(shelterId)
            .orElseThrow(ShelterNotFoundException::new);
        validateShelterOwner(shelter, userDetails);

        shelter.updateInfo(shelterUpdateDto);

        return ShelterUpdateSuccessDto.builder()
            .shelterId(shelterId)
            .build();
    }

    private void validateShelterOwner(final Shelter shelter, final UserDetailsImpl userDetails) {
        if (!shelter.equalsByAccountEmail(userDetails.getEmail())) {
            throw new ShelterPermissionDeniedException();
        }
    }
}
