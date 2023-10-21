package com.daggle.animory.testutil.fixture;

import com.daggle.animory.domain.pet.entity.Pet;
import com.daggle.animory.domain.pet.entity.PetPolygonProfile;

public class PetPolygonProfileFixture {

    public static PetPolygonProfile getOne(final Pet pet) {

        return PetPolygonProfile.builder()
            .pet(pet)
            .activeness(3)
            .intelligence(3)
            .affinity(2)
            .adaptability(4)
            .athletic(1)
            .build();
    }
}
