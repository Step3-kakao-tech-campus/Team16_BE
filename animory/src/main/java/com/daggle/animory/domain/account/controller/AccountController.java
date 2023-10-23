package com.daggle.animory.domain.account.controller;

import com.daggle.animory.common.Response;
import com.daggle.animory.domain.account.AccountService;
import com.daggle.animory.domain.account.dto.request.AccountLoginDto;
import com.daggle.animory.domain.account.dto.request.EmailValidateDto;
import com.daggle.animory.domain.account.dto.request.ShelterSignUpDto;
import com.daggle.animory.domain.account.dto.response.AccountLoginSuccessDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController implements AccountControllerApi {
    private final AccountService accountService;

    /**
     * 보호소 계정으로 회원가입 API
     */
    @PostMapping("/shelter")
    public Response<Void> signUp(@Valid @RequestBody final ShelterSignUpDto shelterSignUpDto) {
        accountService.registerShelterAccount(shelterSignUpDto);
        return Response.success();
    }

    /**
     * 로그인 API
     */
    @PostMapping("/login")
    public ResponseEntity<Response<AccountLoginSuccessDto>> login(@Valid @RequestBody final AccountLoginDto request) {


        return accountService.loginShelterAccount(request);
    }

    /**
     * 이메일 중복 검증 API
     */
    @PostMapping("/email")
    public Response<Void> validateEmail(@Valid @RequestBody final EmailValidateDto emailValidateDto) {
        accountService.validateEmailDuplication(emailValidateDto);
        return Response.success();
    }
}
