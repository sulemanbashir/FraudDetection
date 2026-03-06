package com.frauddetection.evaluation.domain.service.preprocessing;

import com.frauddetection.evaluation.domain.model.ExchangeRate;
import com.frauddetection.evaluation.domain.model.FraudRule;
import com.frauddetection.evaluation.domain.model.Transaction;
import com.frauddetection.evaluation.domain.valueobject.Money;
import com.frauddetection.evaluation.port.out.ExchangeRateProvider;
import com.frauddetection.shared.enums.EntityType;
import com.frauddetection.shared.enums.OperatorType;
import com.frauddetection.shared.exception.EntityPreprocessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AmountPreprocessorTest {

    @Mock
    private ExchangeRateProvider exchangeRateProvider;

    @InjectMocks
    private AmountPreprocessor amountPreprocessor;

    private Transaction transaction;

    @BeforeEach
    void setUp() {
        transaction = new Transaction(
                new Money(100.0, "EUR"), null, null, null, null);
    }

    @Test
    void extractValue_sameCurrency_returnsAmountDirectly() throws EntityPreprocessingException {
        FraudRule rule = buildRule("EUR", "FOREX");

        String result = amountPreprocessor.extractValue(transaction, rule);

        Assertions.assertEquals("100.0", result);
    }

    @Test
    void extractValue_differentCurrency_convertsAmount() throws EntityPreprocessingException {
        FraudRule rule = buildRule("USD", "FOREX");

        ExchangeRate exchangeRate = new ExchangeRate("FOREX", "EUR", "USD", 2.0);
        when(exchangeRateProvider.findExchangeRate("FOREX", "EUR", "USD"))
                .thenReturn(Optional.of(exchangeRate));

        String result = amountPreprocessor.extractValue(transaction, rule);

        Assertions.assertEquals("200.0", result);
    }

    private FraudRule buildRule(String toCurrency, String exchangeCode) {
        return new FraudRule("amount-rule", "KFC", "OPERATIONAL", EntityType.AMOUNT,
                OperatorType.GREATER_THAN, 40.0, null, "500", null, exchangeCode, toCurrency);
    }
}
