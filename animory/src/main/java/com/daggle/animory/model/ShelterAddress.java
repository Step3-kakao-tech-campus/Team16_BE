package com.daggle.animory.model;

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
    private String province;

    private String city;

    private String roadName;

    private String detail;

}