package com.daggle.animory.domain.shelter.entity;

public enum Province {
    서울,
    부산,
    대구,
    인천,
    광주,
    대전,
    울산,
    세종,
    경기,
    강원,
    충북,
    충남,
    전북,
    전남,
    경북,
    경남,
    제주;

    public String getFullProvinceName() {
        return switch (this) {
            case 서울 -> "서울특별시";
            case 부산 -> "부산광역시";
            case 대구 -> "대구광역시";
            case 인천 -> "인천광역시";
            case 광주 -> "광주광역시";
            case 대전 -> "대전광역시";
            case 울산 -> "울산광역시";
            case 세종 -> "세종특별자치시";
            case 경기 -> "경기도";
            case 강원 -> "강원도";
            case 충북 -> "충청북도";
            case 충남 -> "충청남도";
            case 전북 -> "전라북도";
            case 전남 -> "전라남도";
            case 경북 -> "경상북도";
            case 경남 -> "경상남도";
            case 제주 -> "제주특별자치도";
        };
    }
}
