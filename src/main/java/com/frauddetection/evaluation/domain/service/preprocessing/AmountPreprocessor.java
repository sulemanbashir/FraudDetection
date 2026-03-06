package com.frauddetection.evaluation.domain.service.preprocessing;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.frauddetection.evaluation.domain.model.ExchangeRate;
import com.frauddetection.evaluation.domain.model.FraudRule;
import com.frauddetection.evaluation.domain.model.Transaction;
import com.frauddetection.evaluation.port.out.ExchangeRateProvider;
import com.frauddetection.shared.exception.EntityPreprocessingException;

@Component
public class AmountPreprocessor implements EntityPreprocessor {
    private static final Logger log = LogManager.getLogger(AmountPreprocessor.class);

    private final ExchangeRateProvider exchangeRateProvider;

    public AmountPreprocessor(ExchangeRateProvider exchangeRateProvider) {
        this.exchangeRateProvider = exchangeRateProvider;
    }

    @Override
    public String extractValue(Transaction transaction, FraudRule rule)
            throws EntityPreprocessingException {

        if (transaction == null || rule == null) {
            throw new EntityPreprocessingException("Transaction and FraudRule cannot be null");
        }

        if (transaction.money() == null || transaction.money().amount() == null) {
            throw new EntityPreprocessingException("Transaction amount cannot be null");
        }

        String transactionCurrency = transaction.money().currency();
        if (transactionCurrency == null || transactionCurrency.trim().isEmpty()) {
            throw new EntityPreprocessingException("Transaction currency cannot be null or empty");
        }

        if (rule.toCurrencyCode() == null || rule.toCurrencyCode().trim().isEmpty()) {
            throw new EntityPreprocessingException("Fraud rule target currency cannot be null or empty");
        }

        log.info("Checking currencies for rule [{}]", rule.name());
        if (transactionCurrency.equalsIgnoreCase(rule.toCurrencyCode())) {
            log.info("Currencies are equal. Using transaction amount directly");
            return transaction.money().amount().toString();
        }

        log.info("Converting currency for rule [{}]", rule.name());
        ExchangeRate exchangeRate = exchangeRateProvider
                .findExchangeRate(rule.exchangeCode(), transactionCurrency, rule.toCurrencyCode())
                .orElseThrow(() -> new EntityPreprocessingException(
                        String.format("No exchange rate found for exchangeCode=%s, from=%s, to=%s",
                                rule.exchangeCode(), transactionCurrency, rule.toCurrencyCode())));

        Double amount = transaction.money().amount() * exchangeRate.rate();
        log.info("Converted amount: {}", amount);
        return amount.toString();
    }
}
