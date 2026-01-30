package com.frauddetection.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.frauddetection.model.ExchangeRates;

@Repository
public interface ExchangeRatesRepository extends JpaRepository<ExchangeRates, Long> {

    public ExchangeRates findByExchangeCodeAndFromCurrencyCodeAndToCurrencyCode(String exhangeCode, String fromCurrencyCode, String toCurrencyCode);
}
