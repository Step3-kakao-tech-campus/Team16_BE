package com.daggle.animory.domain.shelter.entity;

import lombok.*;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShelterAddress {

    @NotNull
    private Province province;

    private String city;

    private String roadName;

    private String detail;

}