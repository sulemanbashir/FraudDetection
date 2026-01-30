package com.frauddetection.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.frauddetection.validation.groups.TerminalThreadScoreValidator;

public class TransactionInfo {

    private Double amount;
    private String currency;
    @NotEmpty
    private String terminalId;
    @NotEmpty
    private String merchant;
    @NotBlank(groups = TerminalThreadScoreValidator.class)
    private String terminalThreatScore;
    private String city;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public String getTerminalThreatScore() {
        return terminalThreatScore;
    }

    public void setTerminalThreatScore(String terminalThreatScore) {
        this.terminalThreatScore = terminalThreatScore;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

}
