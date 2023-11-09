package com.daggle.animory.domain.shortform.service;

import com.daggle.animory.domain.pet.entity.PetVideo;
import com.daggle.animory.domain.shortform.entity.PetVideoLike;
import com.daggle.animory.domain.shortform.exception.AlreadyLikedPetVideoException;
import com.daggle.animory.domain.shortform.exception.NotLikedPetVideoException;
import com.daggle.animory.domain.shortform.exception.ShortFormNotFoundException;
import com.daggle.animory.domain.shortform.repository.PetVideoJpaRepository;
import com.daggle.animory.domain.shortform.repository.PetVideoLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PetVideoLikeService {
    final PetVideoJpaRepository petVideoJpaRepository;
    final PetVideoLikeRepository petVideoLikeRepository;

    public void updatePetVideoLikeCount(final String IPAddress, final int petVideoId) {
        validatePetVideoLikeDuplication(IPAddress, petVideoId);

        final PetVideo petVideo = petVideoJpaRepository.findById(petVideoId)
            .orElseThrow(ShortFormNotFoundException::new);

        petVideo.updateLikeCount();

        petVideoLikeRepository.save(PetVideoLike.builder()
                                        .ipAddress(IPAddress)
                                        .petVideo(petVideo)
                                        .build());
    }

    public void deletePetVideoLikeCount(final String ipAddress, final int petVideoId) {
        validatePetVideoLikeDelete(ipAddress, petVideoId);

        final PetVideo petVideo = petVideoJpaRepository.findById(petVideoId)
            .orElseThrow(ShortFormNotFoundException::new);

        petVideo.deleteLikeCount();

        petVideoLikeRepository.deleteByIpAddressAndPetVideoId(ipAddress, petVideoId);
    }

    private void validatePetVideoLikeDuplication(final String IPAddress, final int petVideoId) {
        if (petVideoLikeRepository.existsByIpAddressAndPetVideoId(IPAddress, petVideoId)) {
            throw new AlreadyLikedPetVideoException();
        }
    }

    private void validatePetVideoLikeDelete(final String IPAddress, final int petVideoId) {
        if (!petVideoLikeRepository.existsByIpAddressAndPetVideoId(IPAddress, petVideoId)) {
            throw new NotLikedPetVideoException();
        }
    }
}
