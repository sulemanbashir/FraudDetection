package com.frauddetection.enums;

public enum TimeUnit {

    /**
     * Seconds time unit
     */
    SECOND("S", "Seconds"),

    /**
     * Minutes time unit
     */
    MINUTE("M", "Minutes"),

    /**
     * Hours time unit
     */
    HOUR("H", "Hours"),

    /**
     * Days time unit
     */
    DAY("D", "Days"),

    /**
     * Weeks time unit
     */
    WEEK("W", "Weeks"),

    /**
     * Months time unit
     */
    MONTH("O", "Months"),

    /**
     * Years time unit
     */
    YEAR("Y", "Years");

    private final String code;
    private final String description;

    TimeUnit(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static TimeUnit fromCode(String code) {
        if (code == null) {
            throw new IllegalArgumentException("Time unit code cannot be null");
        }

        for (TimeUnit unit : values()) {
            if (unit.code.equalsIgnoreCase(code)) {
                return unit;
            }
        }

        throw new IllegalArgumentException("Unknown time unit code: " + code);
    }

    public static boolean isValid(String code) {
        if (code == null) {
            return false;
        }

        for (TimeUnit unit : values()) {
            if (unit.code.equalsIgnoreCase(code)) {
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
