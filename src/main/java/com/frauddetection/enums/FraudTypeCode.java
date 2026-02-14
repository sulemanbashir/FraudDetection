package com.frauddetection.enums;

public enum FraudTypeCode {

    /**
     * Operational fraud rules
     * Real-time rules evaluated during transaction processing
     */
    OPERATIONAL("O", "Operational Fraud"),

    /**
     * Behavioral fraud rules
     * Pattern-based rules looking at historical behavior
     */
    BEHAVIORAL("B", "Behavioral Fraud"),

    /**
     * Velocity fraud rules
     * Rules based on transaction frequency/velocity
     */
    VELOCITY("V", "Velocity Fraud"),

    /**
     * Geo-location fraud rules
     * Rules based on geographic patterns
     */
    GEO("G", "Geo-location Fraud");

    private final String code;
    private final String description;

    FraudTypeCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static FraudTypeCode fromCode(String code) {
        if (code == null) {
            throw new IllegalArgumentException("Fraud type code cannot be null");
        }

        for (FraudTypeCode type : values()) {
            if (type.code.equalsIgnoreCase(code)) {
                return type;
            }
        }

        throw new IllegalArgumentException("Unknown fraud type code: " + code);
    }

    public static boolean isValid(String code) {
        if (code == null) {
            return false;
        }

        for (FraudTypeCode type : values()) {
            if (type.code.equalsIgnoreCase(code)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        return code;
    }
}
