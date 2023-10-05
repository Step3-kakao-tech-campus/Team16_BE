package com.daggle.animory.domain.account;

import com.daggle.animory.common.error.exception.BadRequest400;
import com.daggle.animory.domain.account.dto.request.EmailValidateDto;
import com.daggle.animory.domain.account.dto.request.AccountLoginDto;
import com.daggle.animory.domain.account.dto.request.ShelterSignUpDto;
import com.daggle.animory.domain.account.dto.response.AccountLoginSuccessDto;
import com.daggle.animory.domain.account.entity.Account;
import com.daggle.animory.domain.account.entity.AccountRole;
import com.daggle.animory.domain.shelter.ShelterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class AccountService {
    private final ShelterRepository shelterRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void registerShelterAccount(final ShelterSignUpDto shelterSignUpDto) {
        Account newAccount = accountRepository.save(
                shelterSignUpDto.getAccount(passwordEncoder));

        shelterRepository.save(shelterSignUpDto.getShelter(newAccount));
    }

    public AccountLoginSuccessDto loginShelterAccount(final AccountLoginDto accountLoginDto) {
        Account account = accountRepository.findByEmail(accountLoginDto.email())
                .orElseThrow(() -> new BadRequest400("이메일 또는 비밀번호를 확인해주세요."));

        if (!passwordEncoder.matches(accountLoginDto.password(), account.getPassword())) {
            throw new BadRequest400("이메일 또는 비밀번호를 확인해주세요.");
        }

        return AccountLoginSuccessDto.builder()
                .id(account.getId())
                .accountRole(AccountRole.SHELTER)
                .build();
    }

    public void validateEmailDuplication(final EmailValidateDto emailValidateDto) {
        if (accountRepository.existsByEmail(emailValidateDto.email()))
            throw new BadRequest400("존재하는 이메일입니다.");
    }
}
