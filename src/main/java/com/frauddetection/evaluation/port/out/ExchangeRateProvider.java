package com.frauddetection.evaluation.port.out;

import com.frauddetection.evaluation.domain.model.ExchangeRate;

import java.util.Optional;

public interface ExchangeRateProvider {

    Optional<ExchangeRate> findExchangeRate(String exchangeCode, String fromCurrency, String toCurrency);
}
