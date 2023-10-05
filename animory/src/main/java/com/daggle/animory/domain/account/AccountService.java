package com.daggle.animory.domain.account;

import com.daggle.animory.common.error.exception.BadRequest400;
import com.daggle.animory.domain.account.dto.request.EmailValidateDto;
import com.daggle.animory.domain.account.dto.request.AccountLoginDto;
import com.daggle.animory.domain.account.dto.request.ShelterSignUpDto;
import com.daggle.animory.domain.account.dto.response.AccountLoginSuccessDto;
import com.daggle.animory.domain.account.entity.Account;
import com.daggle.animory.domain.shelter.ShelterRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class AccountService {
    private final ShelterRepository shelterRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public void registerShelterAccount(final ShelterSignUpDto shelterSignUpDto) {
        Account newAccount = accountRepository.save(
                shelterSignUpDto.getAccount(passwordEncoder));

        shelterRepository.save(shelterSignUpDto.getShelter(newAccount));
    }

    public AccountLoginSuccessDto loginShelterAccount(final AccountLoginDto accountLoginDto) {
        throw new NotImplementedException("NotImplemented yet");
    }

    public void validateEmailDuplication(final EmailValidateDto emailValidateDto) {
        if (accountRepository.existsByEmail(emailValidateDto.email()))
            throw new BadRequest400("존재하는 이메일입니다.");
    }
}
