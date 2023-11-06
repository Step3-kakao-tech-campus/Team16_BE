package com.daggle.animory.domain.shelter;

import com.daggle.animory.domain.account.AccountRepository;
import com.daggle.animory.domain.account.entity.Account;
import com.daggle.animory.domain.account.entity.AccountRole;
import com.daggle.animory.domain.shelter.entity.Province;
import com.daggle.animory.domain.shelter.entity.Shelter;
import com.daggle.animory.domain.shelter.entity.ShelterAddress;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ShelterRepositoryTest {

    @Autowired
    private ShelterRepository shelterRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Test
    void findAllByKakaoLocationIdIn() {
        // given
        final Shelter shelter = Shelter.builder()
            .name("테스트 보호소")
            .address(
                ShelterAddress.builder()
                    .province(Province.광주)
                    .kakaoLocationId(123456789)
                    .build()
            )
            .build();
        shelterRepository.save(shelter);

        // when
        final List<Shelter> foundShelters = shelterRepository.findAllByKakaoLocationIdIn(List.of(123456789, 1, 2, 3,
                                                                                                 4));

        // then
        assertThat(foundShelters).contains(shelter);
    }

    @Test
    void findByAccountEmail() {
        // given
        final Account account = Account.builder()
            .email("aa@naver.cc")
            .password("asd!2weW")
            .role(AccountRole.SHELTER)
            .build();

        final Shelter shelter = Shelter.builder()
            .name("테스트 보호소")
            .address(
                ShelterAddress.builder()
                    .province(Province.광주)
                    .kakaoLocationId(123456789)
                    .build()
            )
            .account(account)
            .build();

        accountRepository.save(account);
        shelterRepository.save(shelter);

        // when
        Shelter findShelter = shelterRepository.findByAccountEmail(account.getEmail()).get();

        // then
        assertThat(account.getEmail()).contains(findShelter.getAccount().getEmail());
    }
}