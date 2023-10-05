package com.daggle.animory.domain.shelter;

import com.daggle.animory.domain.shelter.entity.Shelter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShelterRepository extends JpaRepository<Shelter, Integer> {
    Optional<Shelter> findByAccountId(Integer accountId);
}
