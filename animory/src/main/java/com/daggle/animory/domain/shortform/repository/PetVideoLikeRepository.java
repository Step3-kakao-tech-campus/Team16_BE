package com.daggle.animory.domain.shortform.repository;

import com.daggle.animory.domain.shortform.entity.PetVideoLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetVideoLikeRepository extends JpaRepository<PetVideoLike, Integer> {

    boolean existsByIpAddressAndPetVideoId(String ipAddress, Integer petVideoId);

    void deleteByIpAddressAndPetVideoId(String ipAddress, Integer petVideoId);
}
