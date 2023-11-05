package com.daggle.animory.domain.shelter.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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

    @JsonCreator
    public static Province fromJson(@JsonProperty("province") final String province) {
        return switch (province) {
            case "서울특별시", "서울시", "서울" -> 서울;
            case "부산광역시", "부산시", "부산" -> 부산;
            case "대구광역시", "대구시", "대구" -> 대구;
            case "인천광역시", "인천시", "인천" -> 인천;
            case "광주광역시", "광주시", "광주" -> 광주;
            case "대전광역시", "대전시", "대전" -> 대전;
            case "울산광역시", "울산시", "울산" -> 울산;
            case "세종특별자치시", "세종시", "세종" -> 세종;
            case "경기도", "경기" -> 경기;
            case "강원특별자치도", "강원도", "강원" -> 강원;
            case "충청북도", "충북" -> 충북;
            case "충청남도", "충남" -> 충남;
            case "전라북도", "전북" -> 전북;
            case "전라남도", "전남" -> 전남;
            case "경상북도", "경북" -> 경북;
            case "경상남도", "경남" -> 경남;
            case "제주특별자치도", "제주도", "제주" -> 제주;

            default -> throw new IllegalArgumentException("Unknown Province value: " + province);
        };
    }


}
