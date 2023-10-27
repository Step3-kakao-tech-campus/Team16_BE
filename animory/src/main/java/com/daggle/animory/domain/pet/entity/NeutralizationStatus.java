package com.daggle.animory.domain.pet.entity;

public enum NeutralizationStatus {
    YES("중성화 완료"),
    NO("중성화 안함"),
    UNKNOWN("알 수 없음");

    private final String message;

    NeutralizationStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
