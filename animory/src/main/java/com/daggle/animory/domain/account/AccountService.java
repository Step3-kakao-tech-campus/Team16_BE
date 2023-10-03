package com.daggle.animory.domain.account;

import com.daggle.animory.common.error.exception.BadRequest400;
import com.daggle.animory.domain.account.dto.request.EmailValidateDto;
import com.daggle.animory.domain.account.dto.request.AccountLoginDto;
import com.daggle.animory.domain.account.dto.request.ShelterSignUpDto;
import com.daggle.animory.domain.account.dto.response.AccountLoginSuccessDto;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    public void registerShelterAccount(ShelterSignUpDto shelterSignUpDto) {
        throw new NotImplementedException("NotImplemented yet");
    }

    public AccountLoginSuccessDto loginShelterAccount(AccountLoginDto accountLoginDto) {
        throw new NotImplementedException("NotImplemented yet");
    }

    public void validateEmailDuplication(EmailValidateDto emailValidateDto) {
        if (accountRepository.existsByEmail(emailValidateDto.email()))
            throw new BadRequest400("존재하는 이메일입니다.");
    }
}
