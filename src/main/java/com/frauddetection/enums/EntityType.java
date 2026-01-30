package com.frauddetection.enums;

public enum EntityType {

    /**
     * Transaction amount entity
     * Requires currency conversion preprocessing
     */
    AMOUNT("AMOUNT", "Transaction amount", true),

    /**
     * Currency code entity
     */
    CURRENCY("CURRENCY", "Currency code", false),

    /**
     * Terminal ID entity
     */
    TERMINAL_ID("TERMINAL_ID", "Terminal identifier", false),

    /**
     * Merchant name entity
     */
    MERCHANT("MERCHANT", "Merchant name", false),

    /**
     * Terminal threat score entity
     */
    TERMINAL_THREAT_SCORE("TERMINAL_THREAT_SCORE", "Terminal threat score", false),

    /**
     * Country code entity
     */
    COUNTRY("COUNTRY", "Country code", false),

    /**
     * Card type entity (DEBIT/CREDIT)
     */
    CARD_TYPE("CARD_TYPE", "Card type", false);

    private final String databaseValue;
    private final String description;
    private final boolean requiresPreprocessing;

    EntityType(String databaseValue, String description, boolean requiresPreprocessing) {
        this.databaseValue = databaseValue;
        this.description = description;
        this.requiresPreprocessing = requiresPreprocessing;
    }

    public String getDatabaseValue() {
        return databaseValue;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Indicates if this entity requires preprocessing before evaluation
     * (e.g., AMOUNT needs currency conversion)
     */
    public boolean requiresPreprocessing() {
        return requiresPreprocessing;
    }

    public static EntityType fromDatabaseValue(String databaseValue) {
        if (databaseValue == null) {
            throw new IllegalArgumentException("Entity database value cannot be null");
        }

        for (EntityType type : values()) {
            if (type.databaseValue.equalsIgnoreCase(databaseValue)) {
                return type;
            }
        }

        throw new IllegalArgumentException("Unknown entity type: " + databaseValue);
    }

    public static boolean isValid(String databaseValue) {
        if (databaseValue == null) {
            return false;
        }

        for (EntityType type : values()) {
            if (type.databaseValue.equalsIgnoreCase(databaseValue)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        return databaseValue;
    }
}
