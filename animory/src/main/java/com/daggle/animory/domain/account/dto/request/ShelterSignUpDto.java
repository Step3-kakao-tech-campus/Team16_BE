package com.daggle.animory.domain.account.dto.request;


import com.daggle.animory.domain.account.entity.Account;
import com.daggle.animory.domain.account.entity.AccountRole;
import com.daggle.animory.domain.shelter.entity.Shelter;
import lombok.Builder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Builder
public record ShelterSignUpDto(
        @Email(message = "이메일 형식에 맞지 않습니다.")
        @NotBlank(message = "이메일을 입력해주세요.") String email,
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(?!.*\\s).{8,20}$",
                message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.") String password,
        @NotNull(message = "보호소 이름을 입력해주세요.") String name,
        @NotNull(message = "보호소 연락처를 입력해주세요.") String contact,
        String zonecode,
        @Valid @NotNull(message = "보호소 주소를 입력해주세요.") ShelterAddressSignUpDto address) {

    public Account getAccount(final PasswordEncoder encodePassword) {
        return Account.builder()
                .email(email)
                .password(encodePassword.encode(password))
                .role(AccountRole.SHELTER)
                .build();
    }

    public Shelter getShelter(final Account account) {
        return Shelter.builder()
                .name(name)
                .contact(contact)
                .address(address.getShelterAddress())
                .account(account)
                .build();
    }
}
