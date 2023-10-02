package com.daggle.animory.domain.account;

import com.daggle.animory.domain.account.dto.request.EmailValidateDto;
import com.daggle.animory.domain.account.dto.request.AccountLoginDto;
import com.daggle.animory.domain.account.dto.request.ShelterSignUpDto;
import com.daggle.animory.domain.account.dto.response.AccountLoginSuccessDto;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    public void registerShelterAccount(ShelterSignUpDto shelterSignUpDto) {
        throw new NotImplementedException("NotImplemented yet");
    }

    public AccountLoginSuccessDto loginShelterAccount(AccountLoginDto accountLoginDto) {
        throw new NotImplementedException("NotImplemented yet");
    }

    public void validateEmailDuplication(EmailValidateDto emailValidateDto) {
        throw new NotImplementedException("NotImplemented yet");
    }
}
