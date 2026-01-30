package com.frauddetection.enums;

public enum OperatorType {

    /**
     * Equal comparison operator (=)
     * Example: terminalId = "123"
     */
    EQUAL("EQUAL", "Equal"),

    /**
     * Greater than comparison operator (>)
     * Example: amount > 1000
     */
    GREATER_THAN("GREATER_THAN", "Greater Than"),

    /**
     * Less than comparison operator (<)
     * Example: amount < 100
     */
    LESS_THAN("LESS_THAN", "Less Than"),

    /**
     * IN operator - checks if value exists in a list
     * Example: country IN ('US', 'UK', 'CA')
     */
    IN("IN", "In"),

    /**
     * NOT IN operator - checks if value does not exist in a list
     * Example: country NOT IN ('RU', 'CN')
     */
    NOT_IN("NOT_IN", "Not In"),

    /**
     * LIKE operator - pattern matching
     * Example: email LIKE '%@suspicious.com'
     */
    LIKE("LIKE", "Like");

    private final String databaseValue;
    private final String description;

    OperatorType(String databaseValue, String description) {
        this.databaseValue = databaseValue;
        this.description = description;
    }

    /**
     * Gets the database value (matches b_operator.name)
     */
    public String getDatabaseValue() {
        return databaseValue;
    }

    public String getDescription() {
        return description;
    }

    public static OperatorType fromDatabaseValue(String databaseValue) {
        if (databaseValue == null) {
            throw new IllegalArgumentException("Operator database value cannot be null");
        }

        for (OperatorType type : values()) {
            if (type.description.equalsIgnoreCase(databaseValue)) {
                return type;
            }
        }

        throw new IllegalArgumentException("Unknown operator type: " + databaseValue);
    }

    public static boolean isValid(String databaseValue) {
        if (databaseValue == null) {
            return false;
        }

        for (OperatorType type : values()) {
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
