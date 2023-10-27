package com.daggle.animory.domain.pet.entity;

public enum NeutralizationStatus {
    YES("했어요"),
    NO("안했어요"),
    UNKNOWN("몰라요");

    private final String message;

    NeutralizationStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
