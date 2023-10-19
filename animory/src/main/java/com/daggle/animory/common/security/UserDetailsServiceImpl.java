package com.daggle.animory.common.security;

import com.daggle.animory.common.error.exception.BadRequest400;
import com.daggle.animory.common.error.exception.NotFound404;
import com.daggle.animory.domain.account.AccountRepository;
import com.daggle.animory.domain.account.entity.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Account account = accountRepository.findByEmail(username).orElseThrow(
                () -> new BadRequest400("이메일 또는 비밀번호를 확인해주세요.")
        );
        return new UserDetailsImpl(account);
    }
}
