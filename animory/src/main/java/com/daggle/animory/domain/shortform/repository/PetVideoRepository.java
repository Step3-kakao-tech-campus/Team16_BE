package com.daggle.animory.domain.shortform.repository;

import com.daggle.animory.domain.pet.entity.PetType;
import com.daggle.animory.domain.pet.entity.PetVideo;
import com.daggle.animory.domain.shelter.entity.Province;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PetVideoRepository extends JpaRepository<PetVideo, Integer> {

    @Query("""
        select pv.id
        from PetVideo pv
            left join pv.pet p
            left join p.shelter s
        where s.address.province = :province
            and p.type = :petType
        order by pv.likeCount desc
        """)
    Slice<Integer> findSliceOfIds(PetType petType, Province province, Pageable pageable);

    @Query("""
        select pv.id
        from PetVideo pv
        order by pv.likeCount desc
        """)
    Slice<Integer> findSliceOfIds(Pageable pageable);

    @Query("""
        select pv
        from PetVideo pv
        left join fetch pv.pet p
        left join fetch p.shelter s
        where pv.id in :petVideoIds
        order by pv.likeCount desc
        """)
    List<PetVideo> findAllByPetVideoIdIn(List<Integer> petVideoIds);
}
