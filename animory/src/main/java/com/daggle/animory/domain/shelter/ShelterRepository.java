package com.daggle.animory.domain.shelter;

import com.daggle.animory.domain.shelter.entity.Shelter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShelterRepository extends JpaRepository<Shelter, Integer> {
}
