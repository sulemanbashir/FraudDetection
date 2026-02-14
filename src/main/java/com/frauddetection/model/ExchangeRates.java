package com.frauddetection.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "exchange_rates")
public class ExchangeRates extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "exchange_code")
    private String exchangeCode;

    @Column(name = "from_curr_code")
    private String fromCurrencyCode;

    @Column(name = "to_curr_code")
    private String toCurrencyCode;

    @Column(name = "rate")
    private Double rate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExchangeCode() {
        return exchangeCode;
    }

    public void setExchangeCode(String exchangeCode) {
        this.exchangeCode = exchangeCode;
    }

    public String getFromCurrencyCode() {
        return fromCurrencyCode;
    }

    public void setFromCurrencyCode(String fromCurrencyCode) {
        this.fromCurrencyCode = fromCurrencyCode;
    }

    public String getToCurrencyCode() {
        return toCurrencyCode;
    }

    public void setToCurrencyCode(String toCurrencyCode) {
        this.toCurrencyCode = toCurrencyCode;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ExchangeRates [id=");
        builder.append(id);
        builder.append(", exchangeCode=");
        builder.append(exchangeCode);
        builder.append(", fromCurrencyCode=");
        builder.append(fromCurrencyCode);
        builder.append(", toCurrencyCode=");
        builder.append(toCurrencyCode);
        builder.append(", rate=");
        builder.append(rate);
        builder.append("]");
        return builder.toString();
    }

}
