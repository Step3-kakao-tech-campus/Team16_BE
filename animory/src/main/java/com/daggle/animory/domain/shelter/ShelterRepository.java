package com.daggle.animory.domain.shelter;

import com.daggle.animory.domain.shelter.entity.Shelter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ShelterRepository extends JpaRepository<Shelter, Integer> {
    Optional<Shelter> findByAccountId(Integer accountId);

    @Query("select s " +
        "from Shelter s " +
        "where s.address.kakaoLocationId " +
        "in :shelterLocationIdList")
    List<Shelter> findAllByKakaoLocationIdIn(List<Integer> shelterLocationIdList);
}
