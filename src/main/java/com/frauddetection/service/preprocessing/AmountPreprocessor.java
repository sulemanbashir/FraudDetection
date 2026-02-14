package com.frauddetection.service.preprocessing;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.frauddetection.dto.TransactionInfo;
import com.frauddetection.exception.EntityPreprocessingException;
import com.frauddetection.model.ExchangeRates;
import com.frauddetection.model.FraudParameterDetails;
import com.frauddetection.repository.ExchangeRatesRepository;

/**
 * Entity preprocessor for AMOUNT entity.
 * Handles currency conversion using exchange rates when needed.
 */
@Component
public class AmountPreprocessor implements EntityPreprocessor {
    private static final Logger log = LogManager.getLogger(AmountPreprocessor.class);

    private final ExchangeRatesRepository exchangeRatesRepository;

    public AmountPreprocessor(ExchangeRatesRepository exchangeRatesRepository) {
        this.exchangeRatesRepository = exchangeRatesRepository;
    }

    @Override
    public void normalizeEntity(TransactionInfo transactionInfo, FraudParameterDetails fraudParameterDetails)
            throws EntityPreprocessingException {

        // Validate inputs according to LSP contract
        if (transactionInfo == null || fraudParameterDetails == null) {
            throw new EntityPreprocessingException("TransactionInfo and FraudParameterDetails cannot be null");
        }

        if (transactionInfo.amount() == null) {
            throw new EntityPreprocessingException("Transaction amount cannot be null");
        }

        if (transactionInfo.currency() == null || transactionInfo.currency().trim().isEmpty()) {
            throw new EntityPreprocessingException("Transaction currency cannot be null or empty");
        }

        if (fraudParameterDetails.getToCurrCode() == null || fraudParameterDetails.getToCurrCode().trim().isEmpty()) {
            throw new EntityPreprocessingException("Fraud rule target currency cannot be null or empty");
        }

        // Check if currency conversion is needed
        log.info("Checking request and fraud rule[" + fraudParameterDetails.getFraudRuleName() + "] Currency are equal.");
        if (transactionInfo.currency().equalsIgnoreCase(fraudParameterDetails.getToCurrCode())) {
            log.info("Currencies are equal. Using transaction amount directly");
            // IMPORTANT: Always set actualCriteriaValue (LSP compliance)
            fraudParameterDetails.setActualCriteriaValue(transactionInfo.amount().toString());
            return;
        }

        // Perform currency conversion
        log.info("Going to fetch exchangeRates!");
        try {
            ExchangeRates exchangeRates = exchangeRatesRepository.findByExchangeCodeAndFromCurrencyCodeAndToCurrencyCode(
                    fraudParameterDetails.getExchangeCode(),
                    transactionInfo.currency(),
                    fraudParameterDetails.getToCurrCode());

            if (exchangeRates == null) {
                throw new EntityPreprocessingException(
                        String.format("No exchange rate found for exchangeCode=%s, from=%s, to=%s",
                                fraudParameterDetails.getExchangeCode(),
                                transactionInfo.currency(),
                                fraudParameterDetails.getToCurrCode()));
            }

            log.info("Fetched ExchangeRates: " + exchangeRates);
            Double amount = transactionInfo.amount() * exchangeRates.getRate();
            log.info("Setting converted amount in actual criteria value: " + amount);
            fraudParameterDetails.setActualCriteriaValue(amount.toString());

        } catch (Exception e) {
            throw new EntityPreprocessingException(
                    "Failed to preprocess amount with currency conversion", e);
        }
    }

}
