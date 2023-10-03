package com.daggle.animory.domain.account;

import com.daggle.animory.common.Response;
import com.daggle.animory.domain.account.dto.request.EmailValidateDto;
import com.daggle.animory.domain.account.dto.request.AccountLoginDto;
import com.daggle.animory.domain.account.dto.request.ShelterSignUpDto;
import com.daggle.animory.domain.account.dto.response.AccountLoginSuccessDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

    /**
     * 보호소 계정으로 회원가입 API
     */

    @PostMapping("/shelter")
    public Response<?> singUp(@Valid @RequestBody ShelterSignUpDto shelterSignUpDto) {
        accountService.registerShelterAccount(shelterSignUpDto);
        return Response.success();
    }

    /**
     * 로그인 API
     */
    @PostMapping("/login")
    public ResponseEntity<Response<?>> login(@Valid @RequestBody AccountLoginDto request) {
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "token")
                .body(Response.success(
                        accountService.loginShelterAccount(request)
                ));
    }

    /**
     * 이메일 중복 검증 API
     */
    @PostMapping("/email")
    public Response<?> validateEmail(@RequestBody EmailValidateDto emailValidateDto) {
        accountService.validateEmailDuplication(emailValidateDto);
        return Response.success();
    }
}
