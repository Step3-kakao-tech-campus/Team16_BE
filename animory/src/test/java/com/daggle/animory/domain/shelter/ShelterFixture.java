package com.daggle.animory.domain.shelter;

import com.daggle.animory.domain.account.entity.Account;
import com.daggle.animory.domain.shelter.entity.Province;
import com.daggle.animory.domain.shelter.entity.Shelter;
import com.daggle.animory.domain.shelter.entity.ShelterAddress;

import java.util.ArrayList;
import java.util.List;

public class ShelterFixture {

    public static Shelter getOne(Account account) {
        return Shelter.builder()
            .name("테스트 보호소")
            .address(
                ShelterAddress.builder()
                    .province(Province.광주)
                    .city("테스트시군구")
                    .detail("테스트도로명주소")
                    .build()
            )
            .contact("010-1234-5678")
            .account(account)
            .build();
    }

    public static List<Shelter> get(final int n,
                                    final Province province,
                                    final Account account) {
        final List<Shelter> shelters = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            shelters.add(
                Shelter.builder()
                    .name("테스트 보호소" + i)
                    .address(
                        ShelterAddress.builder()
                            .province(province)
                            .city("테스트시군구" + i)
                            .detail("테스트도로명주소" + i)
                            .build()
                    )
                    .contact("010-1234-5678")
                    .account(account)
                    .build());

        }
        return shelters;
    }

}
