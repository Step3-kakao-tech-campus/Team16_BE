package com.daggle.animory.domain;

import com.daggle.animory.domain.shelter.Province;
import com.daggle.animory.domain.shelter.Shelter;
import com.daggle.animory.domain.shelter.ShelterAddress;

import java.util.ArrayList;
import java.util.List;

public class ShelterFixture {

    public static Shelter getOne() {
        return Shelter.builder()
            .name("테스트 보호소")
            .address(null) // TODO: ShelterAddress Fixture
            .contact("010-1234-5678")
            .account(null) // TODO: Account Fixture
            .build();
    }

    public static List<Shelter> get(final int n,
                                    final Province province) {
        final List<Shelter> shelters = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            shelters.add(
                Shelter.builder()
                    .name("테스트 보호소" + i)
                    .address(
                        ShelterAddress.builder()
                            .province(province)
                            .city("테스트시")
                            .detail("테스트동")
                            .build()
                    ) // TODO: ShelterAddress Fixture
                    .contact("010-1234-5678")
                    .account(null) // TODO: Account Fixture
                    .build());

        }
        return shelters;
    }

}
