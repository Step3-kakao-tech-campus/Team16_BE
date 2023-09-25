package com.daggle.animory.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Entity
@Builder
@AllArgsConstructor
@Table(name = "shelter")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Shelter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String name;

    private String contact;

    @Embedded
    private ShelterAddress address;

    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;
}