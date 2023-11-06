package com.daggle.animory.domain.account.controller;

import com.daggle.animory.common.Response;
import com.daggle.animory.domain.account.dto.request.AccountLoginDto;
import com.daggle.animory.domain.account.dto.request.EmailValidateDto;
import com.daggle.animory.domain.account.dto.request.ShelterSignUpDto;
import com.daggle.animory.domain.account.dto.response.AccountLoginSuccessDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Tag(name = "계정 API", description = "계정 관련 API 입니다.")
public interface AccountControllerApi {

    @Operation(summary = "보호소 계정으로 회원가입 API", description = "보호소 계정으로 회원가입합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "회원가입 성공"),
        @ApiResponse(responseCode = "400", description = """
            - Email 중복
            - 비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.            
        """, content = @Content),
    })
    Response<Void> signUp(@Valid @RequestBody ShelterSignUpDto shelterSignUpDto);

    @Operation(summary = "로그인 API", description = "ID와 비밀번호를 입력받고 인증에 사용할 토큰을 반환합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "로그인 성공"),
        @ApiResponse(responseCode = "400", description = """
            - ID 또는 Password 불일치로 인한 로그인 실패
            - Email 형식 오류, 비밀번호가 null
        """, content = @Content)
    })
    ResponseEntity<Response<AccountLoginSuccessDto>> login(@Valid @RequestBody AccountLoginDto request);

    @Operation(summary = "이메일 중복 검증 API", description = "회원가입에 사용할 이메일이 중복되는지 검사합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "이메일 중복 검증 성공"),
        @ApiResponse(responseCode = "400", description = "이미 존재하는 이메일인 경우"),
    })
    Response<Void> validateEmail(@Valid @RequestBody EmailValidateDto emailValidateDto);
}
