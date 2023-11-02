package com.daggle.animory.domain.shortform.repository;

import com.daggle.animory.domain.pet.entity.PetType;
import com.daggle.animory.domain.pet.entity.PetVideo;
import com.daggle.animory.domain.shelter.entity.Province;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PetVideoJpqlRepository {
    @PersistenceContext
    private EntityManager em;

    private enum PetVideoFilterCondition {
        BOTH, // 1. petType과 province가 모두 존재하는 경우
        ONLY_PET, // 2. petType만 존재하는 경우
        ONLY_PROVINCE, // 3. province만 존재하는 경우
        NEITHER // 4. petType과 province가 모두 존재하지 않는 경우
    }

    public Slice<Integer> findPetVideoIdsBy(final PetType petType, final Province province, final Pageable pageable) {
        // Build Query
        final String jpql = """
            select pv.id
            from PetVideo pv
            left join pv.pet p
            left join p.shelter s
            %s
            order by pv.likeCount desc""".formatted(switch (getFilterCondition(petType, province)) {
            case BOTH -> " where p.type = :petType and s.address.province = :province";
            case ONLY_PET -> " where p.type = :petType";
            case ONLY_PROVINCE -> " where s.address.province = :province";
            case NEITHER -> "";
        });

        final TypedQuery<Integer> query = em.createQuery(jpql, Integer.class);
        if (petType != null) query.setParameter("petType", petType);
        if (province != null) query.setParameter("province", province);

        // Slice, Pagination
        final int limit = pageable.getPageSize();
        final int offset = (int) pageable.getOffset();

        final List<Integer> petVideoIds = query
            .setFirstResult(offset)
            .setMaxResults(limit + 1) // slice 구현체는 기대하는 사이즈 보다 1개 더 가져온다.
            .getResultList();

        final boolean hasNext = (petVideoIds.size() == limit + 1); // 기대했던 사이즈보다 1개 더 많으면 hasNext = true

        if (hasNext) petVideoIds.remove(petVideoIds.size() - 1); // 마지막 요소 제거

        return new SliceImpl<>(petVideoIds, pageable, hasNext);
    }

    public List<PetVideo> findAllByIds(final List<Integer> petVideoIds) {
        if (petVideoIds.isEmpty()) return new ArrayList<>(); // in 절이 비어있으면 쿼리 에러.

        final String jpql = """
            select pv
            from PetVideo pv
            left join fetch pv.pet p
            left join fetch p.shelter s
            where pv.id in :petVideoIds
            order by pv.likeCount desc
            """;

        return em.createQuery(jpql, PetVideo.class)
            .setParameter("petVideoIds", petVideoIds)
            .getResultList();
    }

    private static PetVideoFilterCondition getFilterCondition(final PetType petType, final Province province) {
        if (petType != null && province != null) {
            return PetVideoFilterCondition.BOTH;
        } else if (petType != null) {
            return PetVideoFilterCondition.ONLY_PET;
        } else if (province != null) {
            return PetVideoFilterCondition.ONLY_PROVINCE;
        } else {
            return PetVideoFilterCondition.NEITHER;
        }
    }

}
