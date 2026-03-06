package com.frauddetection.evaluation.adapter.out.config;

import java.util.Optional;

import com.frauddetection.evaluation.domain.model.ExchangeRate;
import com.frauddetection.evaluation.port.out.ExchangeRateProvider;
import org.springframework.stereotype.Component;

@Component
public class YamlExchangeRateProvider implements ExchangeRateProvider {

    private final FraudDetectionProperties properties;

    public YamlExchangeRateProvider(FraudDetectionProperties properties) {
        this.properties = properties;
    }

    @Override
    public Optional<ExchangeRate> findExchangeRate(String exchangeCode, String fromCurrency, String toCurrency) {
        return properties.getExchangeRates().stream()
                .filter(r -> r.exchangeCode().equalsIgnoreCase(exchangeCode)
                        && r.fromCurrency().equalsIgnoreCase(fromCurrency)
                        && r.toCurrency().equalsIgnoreCase(toCurrency))
                .map(r -> new ExchangeRate(r.exchangeCode(), r.fromCurrency(), r.toCurrency(), r.rate()))
                .findFirst();
    }
}
