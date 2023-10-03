package com.daggle.animory.domain.pet.repository;

import com.daggle.animory.domain.pet.entity.Pet;
import com.daggle.animory.domain.pet.entity.PetPolygonProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetPolygonRepository extends JpaRepository<PetPolygonProfile, Integer> {
}
