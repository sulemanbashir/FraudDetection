package com.frauddetection.shared.enums;

public enum EntityType {

    AMOUNT("amount", "Transaction amount", true, DataTypeEnum.DOUBLE),
    CURRENCY("currency", "Currency code", false, DataTypeEnum.STRING),
    TERMINAL_ID("terminalId", "Terminal identifier", false, DataTypeEnum.STRING),
    MERCHANT("merchant", "Merchant name", false, DataTypeEnum.STRING),
    TERMINAL_THREAT_SCORE("terminalThreatScore", "Terminal threat score", false, DataTypeEnum.INTEGER),
    COUNTRY("country", "Country code", false, DataTypeEnum.STRING),
    CARD_TYPE("cardType", "Card type", false, DataTypeEnum.STRING),
    CITY("city", "City", false, DataTypeEnum.STRING);

    private final String value;
    private final String description;
    private final boolean requiresPreprocessing;
    private final DataTypeEnum dataType;

    EntityType(String value, String description, boolean requiresPreprocessing, DataTypeEnum dataType) {
        this.value = value;
        this.description = description;
        this.requiresPreprocessing = requiresPreprocessing;
        this.dataType = dataType;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public boolean requiresPreprocessing() {
        return requiresPreprocessing;
    }

    public DataTypeEnum getDataType() {
        return dataType;
    }

    public static EntityType fromValue(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Entity value cannot be null");
        }
        for (EntityType type : values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown entity type: " + value);
    }

    @Override
    public String toString() {
        return value;
    }
}
