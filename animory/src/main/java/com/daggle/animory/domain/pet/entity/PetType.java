package com.daggle.animory.domain.pet.entity;

public enum PetType {

    DOG,
    CAT,
    ETC;

    public String getKoreanName() {
        return switch (this) {
            case DOG -> "강아지";
            case CAT -> "고양이";
            case ETC -> "기타";
        };
    }
}
