package com.daggle.animory.domain.shortform.repository;

import com.daggle.animory.domain.pet.entity.Pet;
import com.daggle.animory.domain.pet.entity.PetType;
import com.daggle.animory.domain.shelter.entity.Province;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PetVideoRepository extends JpaRepository<Pet, Integer> {
    @Query("select p " +
        "from Pet p " +
        "join fetch p.shelter s " +
        "join fetch p.petVideos pv ")
    Slice<Pet> findSliceBy(Pageable pageable);

    @Query("select p " +
        "from Pet p " +
        "join fetch p.shelter s " +
        "join fetch p.petVideos pv " +
        "where s.address.province = :province " +
        "and p.type = :petType")
    Slice<Pet> findSliceBy(PetType petType, Province province, Pageable searchCondition);
}
