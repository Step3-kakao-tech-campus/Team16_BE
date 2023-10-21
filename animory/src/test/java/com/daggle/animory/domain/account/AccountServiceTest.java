package com.daggle.animory.domain.account;

import com.daggle.animory.domain.account.dto.request.ShelterAddressSignUpDto;
import com.daggle.animory.domain.account.dto.request.ShelterSignUpDto;
import com.daggle.animory.domain.account.entity.Account;
import com.daggle.animory.domain.shelter.ShelterRepository;
import com.daggle.animory.domain.shelter.entity.Province;
import com.daggle.animory.domain.shelter.entity.Shelter;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
    @InjectMocks
    AccountService accountService;
    @Mock
    AccountRepository accountRepository;
    @Mock
    ShelterRepository shelterRepository;
    @Mock
    PasswordEncoder passwordEncoder;

    @Nested
    class 보호소_회원가입 {

        @Test
        void 성공_보호소_회원가입() {
            // given
            ShelterSignUpDto signUpDto = ShelterSignUpDto.builder()
                    .email("sdf@adf.cds")
                    .password("fdafqQ12@")
                    .name("aaa")
                    .contact("010123")
                    .zonecode("123")
                    .address(ShelterAddressSignUpDto.builder()
                            .province(Province.광주)
                            .city("dsfad")
                            .roadName("dafsd")
                            .detail("safd")
                            .build())
                    .build();

            accountService.registerShelterAccount(signUpDto);

            verify(accountRepository, times(1)).save(any(Account.class));
            verify(shelterRepository, times(1)).save(any(Shelter.class));
        }
    }
}
