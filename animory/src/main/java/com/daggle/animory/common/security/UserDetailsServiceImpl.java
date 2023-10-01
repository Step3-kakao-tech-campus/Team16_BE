package com.daggle.animory.common.security;

import com.daggle.animory.domain.account.Account;
import com.daggle.animory.domain.account.AccountRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 사용자입니다."));
        return new UserDetailsImpl(account);
    }
}
