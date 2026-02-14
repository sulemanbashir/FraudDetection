package com.frauddetection.service.preprocessing;

import com.frauddetection.dto.TransactionInfo;
import com.frauddetection.exception.EntityPreprocessingException;
import com.frauddetection.model.ExchangeRates;
import com.frauddetection.model.FraudParameterDetails;
import com.frauddetection.repository.ExchangeRatesRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AmountPreprocessorTest {

    @Mock
    private ExchangeRatesRepository exchangeRatesRepository;

    @InjectMocks
    private AmountPreprocessor amountPreprocessor;

    private TransactionInfo transactionInfo;
    private FraudParameterDetails fraudParameterDetails;

    @BeforeEach
    void setUp() {
        transactionInfo = new TransactionInfo(100.0, "EUR", null, null, null, null);

        fraudParameterDetails = new FraudParameterDetails();
        fraudParameterDetails.setFraudRuleName("amount-rule");
        fraudParameterDetails.setExchangeCode("FOREX");
    }

    @Test
    void normalizeEntity_sameCurrency_setsAmountDirectly() throws EntityPreprocessingException {
        fraudParameterDetails.setToCurrCode("EUR");

        amountPreprocessor.normalizeEntity(transactionInfo, fraudParameterDetails);

        Assertions.assertEquals("100.0", fraudParameterDetails.getActualCriteriaValue());
    }

    @Test
    void normalizeEntity_differentCurrency_convertsAmount() throws EntityPreprocessingException {
        fraudParameterDetails.setToCurrCode("USD");

        ExchangeRates exchangeRates = new ExchangeRates();
        exchangeRates.setExchangeCode("FOREX");
        exchangeRates.setFromCurrencyCode("EUR");
        exchangeRates.setToCurrencyCode("USD");
        exchangeRates.setRate(2.0);

        when(exchangeRatesRepository.findByExchangeCodeAndFromCurrencyCodeAndToCurrencyCode("FOREX", "EUR", "USD"))
                .thenReturn(exchangeRates);

        amountPreprocessor.normalizeEntity(transactionInfo, fraudParameterDetails);

        Assertions.assertEquals("200.0", fraudParameterDetails.getActualCriteriaValue());
    }
}
