package com.frauddetection.evaluation.domain.model;

public record ExchangeRate(String exchangeCode, String fromCurrency, String toCurrency, double rate) {
}
