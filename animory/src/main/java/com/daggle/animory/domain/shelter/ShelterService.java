package com.daggle.animory.domain.shelter;

import com.daggle.animory.domain.shelter.dto.response.ShelterProfilePage;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShelterService {
    private final ShelterRepository shelterRepository;

    public ShelterProfilePage getShelterProfile(Long shelterId, int page) {
        throw new NotImplementedException("Not Impl");
    }
}
