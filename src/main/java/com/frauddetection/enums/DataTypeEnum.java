package com.frauddetection.enums;

/**
 * Enumeration of supported data types for fraud rule entities.
 * Maps to b_datatypes.name in database (VARCHAR).
 * <p>
 * These determine how values are compared in fraud rules.
 */
public enum DataTypeEnum {

    /**
     * String/text data type
     * Used for: terminalId, merchant, country, etc.
     */
    STRING("STRING", "String data type"),

    /**
     * Double/decimal numeric data type
     * Used for: amount, score, rate, etc.
     */
    DOUBLE("DOUBLE", "Double/decimal numeric data type"),

    /**
     * Integer numeric data type
     * Used for: count, threshold, etc.
     */
    INTEGER("INTEGER", "Integer numeric data type"),

    /**
     * Date/time data type
     * Used for: transaction timestamp, etc.
     */
    DATETIME("DATETIME", "Date/time data type");

    private final String databaseValue;
    private final String description;

    DataTypeEnum(String databaseValue, String description) {
        this.databaseValue = databaseValue;
        this.description = description;
    }

    public String getDatabaseValue() {
        return databaseValue;
    }

    public String getDescription() {
        return description;
    }

    public static DataTypeEnum fromDatabaseValue(String databaseValue) {
        if (databaseValue == null) {
            throw new IllegalArgumentException("DataType database value cannot be null");
        }

        for (DataTypeEnum type : values()) {
            if (type.databaseValue.equalsIgnoreCase(databaseValue)) {
                return type;
            }
        }

        throw new IllegalArgumentException("Unknown data type: " + databaseValue);
    }

    public static boolean isValid(String databaseValue) {
        if (databaseValue == null) {
            return false;
        }

        for (DataTypeEnum type : values()) {
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
