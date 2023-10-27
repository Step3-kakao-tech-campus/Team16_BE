package com.daggle.animory.domain.shelter.entity;

import com.daggle.animory.common.entity.BaseEntity;
import com.daggle.animory.domain.account.entity.Account;
import com.daggle.animory.domain.shelter.dto.request.ShelterUpdateDto;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Entity
@Builder
@AllArgsConstructor
@Table(name = "shelter")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Shelter extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String name;

    private String contact;

    @Embedded
    private ShelterAddress address;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    public void updateInfo(ShelterUpdateDto shelterUpdateDto) {
        this.name = shelterUpdateDto.name();
        this.contact = shelterUpdateDto.contact();
        this.address = shelterUpdateDto.shelterAddressUpdateDto().getShelterAddress();
    }
}