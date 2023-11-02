package com.daggle.animory.domain.shortform.repository;

import com.daggle.animory.domain.pet.entity.PetVideo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PetVideoJpaRepository extends JpaRepository<PetVideo, Integer> {


    @Query("""
        select pv.id
        from PetVideo pv
        order by pv.likeCount desc
        """)
    Slice<Integer> findPetVideoIdsBy(Pageable pageable);

}
