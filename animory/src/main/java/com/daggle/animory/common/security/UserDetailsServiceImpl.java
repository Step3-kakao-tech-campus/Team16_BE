package com.daggle.animory.common.security;

import com.daggle.animory.domain.account.AccountRepository;
import com.daggle.animory.domain.account.entity.Account;
import com.daggle.animory.domain.account.exception.CheckEmailOrPasswordException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Account account = accountRepository.findByEmail(username).orElseThrow(
                CheckEmailOrPasswordException::new
        );
        return new UserDetailsImpl(account);
    }
}
