package com.daggle.animory.testutil.fixture;

import com.daggle.animory.domain.account.entity.Account;
import com.daggle.animory.domain.account.entity.AccountRole;

import java.util.ArrayList;
import java.util.List;

public class AccountFixture {

    public static final String EMAIL = "test@test.com";
    public static final String PASSWORD = "Test1234!";

    private static final String encodedPassword = "$2a$12$9XLsbe0Qd3Ifd5GBYiv3y.vb4Q0ZK5IOxAmXNw2ENLYNyH3j6JUkq";

    public static Account getUser() {
        return Account.builder()
                .email(EMAIL)
                .password(encodedPassword)
                .role(AccountRole.USER)
            .build();
    }

    public static Account getShelter() {
        return Account.builder()
                .email(EMAIL)
                .password(encodedPassword)
                .role(AccountRole.SHELTER)
                .build();
    }

    public static List<Account> get(final int n,
                                    final AccountRole role) {
        final List<Account> accounts = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            accounts.add(
                    Account.builder()
                            .email(i + EMAIL)
                            .password(PASSWORD)
                            .role(role)
                            .build());

        }
        return accounts;
    }
}
