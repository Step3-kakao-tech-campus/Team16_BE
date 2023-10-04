package com.daggle.animory.domain.pet;

import com.daggle.animory.domain.pet.entity.Pet;
import com.daggle.animory.domain.pet.entity.PetType;
import com.daggle.animory.domain.shelter.entity.Province;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PetRepository extends JpaRepository<Pet, Integer> {

    @Query("select p " +
        "from Pet p " +
        "join fetch p.shelter s " +
        "where s.address.province = :province " +
        "and p.type = :petType")
    Slice<Pet> findSliceBy(@Param("petType") PetType petType, @Param("province") Province province, Pageable searchCondition);

    Slice<Pet> findSliceBy(Pageable pageable);
    @Query("select p " +
            "from Pet p " +
            "where p.shelter.id = :shelterId")
    Page<Pet> findByShelterId(@Param("shelterId")Integer shelterId, Pageable pageable);
}
