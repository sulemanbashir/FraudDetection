package com.frauddetection.evaluation.domain.valueobject;

public enum RiskLevel {

    LOW("L", "Low"),
    MEDIUM("M", "Medium"),
    HIGH("H", "High");

    private final String code;
    private final String label;

    RiskLevel(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public String getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }

    public static RiskLevel fromCode(String code) {
        for (RiskLevel level : values()) {
            if (level.code.equals(code)) {
                return level;
            }
        }
        throw new IllegalArgumentException("Unknown risk level code: " + code);
    }
}
