package com.daggle.animory.domain.account;

import com.daggle.animory.domain.account.entity.Account;
import com.daggle.animory.domain.account.entity.AccountRole;

import java.util.ArrayList;
import java.util.List;

public class AccountFixture {

    public static Account getUser() {
        return Account.builder()
                .email("test@test.com")
                .password("Test1234!")
                .role(AccountRole.USER)
            .build();
    }

    public static Account getShelter() {
        return Account.builder()
                .email("shelter@test.com")
                .password("Test1234!")
                .role(AccountRole.SHELTER)
                .build();
    }

    public static List<Account> get(final int n,
                                    AccountRole role) {
        final List<Account> accounts = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            accounts.add(
                    Account.builder()
                            .email(i + "test@test.com")
                            .password("Test1234!")
                            .role(role)
                            .build());

        }
        return accounts;
    }
}
