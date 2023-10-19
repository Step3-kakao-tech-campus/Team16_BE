package com.daggle.animory.common.security;

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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("사용자를 찾을 수 없습니다.")
        );
        return new UserDetailsImpl(account);
    }
}
