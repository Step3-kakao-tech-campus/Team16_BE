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

    public Slice<Integer> findPetVideoIdsBy(PetType petType, Province province, Pageable pageable) {
        String jpql = """
            select pv.id
            from PetVideo pv
            left join pv.pet p
            left join p.shelter s
            """;
        String where = "";
        List<String> conditions = new ArrayList<>();
        String order = " order by pv.likeCount desc";

        if (petType != null) conditions.add("p.type = :petType");

        if (province != null) conditions.add("s.address.province = :province");

        if (!conditions.isEmpty()) where = " where " + String.join(" and ", conditions);

        jpql += where + order;

        TypedQuery<Integer> query = em.createQuery(jpql, Integer.class);

        if (petType != null) {
            query.setParameter("petType", petType);
        }
        if (province != null) {
            query.setParameter("province", province);
        }

        int overLimit = pageable.getPageSize() + 1;
        int offset = (int) pageable.getOffset();

        List<Integer> petVideoIds = query.setFirstResult(offset)
            .setMaxResults(overLimit)
            .getResultList();

        boolean hasNext = petVideoIds.size() == overLimit;

        if (hasNext) {
            petVideoIds.remove(petVideoIds.size() - 1);
        }

        return new SliceImpl<>(petVideoIds, pageable, hasNext);
    }

    public List<PetVideo> findAllByIds(final List<Integer> petVideoIds) {
        if (petVideoIds.isEmpty()) return new ArrayList<>(); // in 절이 비어있으면 쿼리 에러.

        String jpql = """
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
}
