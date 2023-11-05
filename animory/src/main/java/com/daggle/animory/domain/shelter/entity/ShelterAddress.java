package com.daggle.animory.domain.shelter.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShelterAddress {

    // 카카오 API에서 제공하는 주소 ID
    // kakao Id와 x, y 좌표는 가입 프로세스가 자동화 된다면 @NotNull이 될 수 있음.
    private Integer kakaoLocationId;

    private double x;

    private double y;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Province province;

    private String city;

    private String roadName;

    private String detail;

    @Column(length = 10, name = "zone_code")
    private String zonecode;

}