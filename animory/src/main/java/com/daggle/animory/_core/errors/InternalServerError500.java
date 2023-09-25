package com.daggle.animory._core.errors;

import com.daggle.animory._core.utils.ApiResult;
import com.daggle.animory._core.utils.ApiUtils;
import lombok.Getter;
import org.springframework.http.HttpStatus;


// 유효성 검사 실패, 잘못된 파라메터 요청
@Getter
public class InternalServerError500 extends RuntimeException {

    public InternalServerError500(String message) {
        super(message);
    }

    public ApiResult body(){
        return ApiUtils.error(getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public HttpStatus status(){
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}