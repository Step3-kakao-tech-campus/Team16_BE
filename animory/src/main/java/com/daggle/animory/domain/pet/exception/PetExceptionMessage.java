package com.daggle.animory.domain.pet.exception;

public enum PetExceptionMessage {
    PET_NOT_FOUND("해당하는 동물이 존재하지 않습니다."),
    // TODO: 이미 있는 동물인지 확인하는 로직이 필요할까? 판단하기 애매하다면 같은 보호소 내 펫 이름 중복 가능?
    PET_ALREADY_EXIST("이미 존재하는 동물입니다."),
    INVALID_PET_AGE_FORMAT("잘못된 나이 형식입니다.: "),
    INVALID_PET_YEAR_RANGE("잘못된 년도 수 범위입니다: "),
    INVALID_PET_MONTH_RANGE("잘못된 개월 수 범위입니다: "),
    PET_PERMISSION_DENIED("해당 동물에 대한 권한이 없습니다.");
    private final String message;

    PetExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
