package com.daggle.animory.domain.pet.entity;

public enum AdoptionStatus {
    YES("입양 완료"),
    NO("미입양"),
    UNABLE("입양 불가");

    private String message;

    AdoptionStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
