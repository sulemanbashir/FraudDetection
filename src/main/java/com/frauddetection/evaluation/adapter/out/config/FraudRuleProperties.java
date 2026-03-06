package com.frauddetection.evaluation.adapter.out.config;

public class FraudRuleProperties {

    private String name;
    private String merchant;
    private String fraudType;
    private String entityType;
    private String operatorType;
    private Double fraudScore;
    private Integer terminalThreatThreshold;
    private String thresholdValue;
    private String thresholdUpperValue;
    private String exchangeCode;
    private String toCurrencyCode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public String getFraudType() {
        return fraudType;
    }

    public void setFraudType(String fraudType) {
        this.fraudType = fraudType;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(String operatorType) {
        this.operatorType = operatorType;
    }

    public Double getFraudScore() {
        return fraudScore;
    }

    public void setFraudScore(Double fraudScore) {
        this.fraudScore = fraudScore;
    }

    public Integer getTerminalThreatThreshold() {
        return terminalThreatThreshold;
    }

    public void setTerminalThreatThreshold(Integer terminalThreatThreshold) {
        this.terminalThreatThreshold = terminalThreatThreshold;
    }

    public String getThresholdValue() {
        return thresholdValue;
    }

    public void setThresholdValue(String thresholdValue) {
        this.thresholdValue = thresholdValue;
    }

    public String getThresholdUpperValue() {
        return thresholdUpperValue;
    }

    public void setThresholdUpperValue(String thresholdUpperValue) {
        this.thresholdUpperValue = thresholdUpperValue;
    }

    public String getExchangeCode() {
        return exchangeCode;
    }

    public void setExchangeCode(String exchangeCode) {
        this.exchangeCode = exchangeCode;
    }

    public String getToCurrencyCode() {
        return toCurrencyCode;
    }

    public void setToCurrencyCode(String toCurrencyCode) {
        this.toCurrencyCode = toCurrencyCode;
    }
}
