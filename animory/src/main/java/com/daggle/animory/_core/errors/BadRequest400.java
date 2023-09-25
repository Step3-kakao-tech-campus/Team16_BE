package com.daggle.animory._core.errors;

import com.daggle.animory._core.utils.ApiResult;
import com.daggle.animory._core.utils.ApiUtils;
import lombok.Getter;
import org.springframework.http.HttpStatus;


// 유효성 검사 실패, 잘못된 파라메터 요청
@Getter
public class BadRequest400 extends RuntimeException {

    public BadRequest400(String message) {
        super(message);
    }

    public ApiResult body(){
        return ApiUtils.error(getMessage(), HttpStatus.BAD_REQUEST);
    }

    public HttpStatus status(){
        return HttpStatus.BAD_REQUEST;
    }
}