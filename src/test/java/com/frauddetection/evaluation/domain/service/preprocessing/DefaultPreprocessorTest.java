package com.frauddetection.evaluation.domain.service.preprocessing;

import com.frauddetection.evaluation.domain.model.FraudRule;
import com.frauddetection.evaluation.domain.model.Transaction;
import com.frauddetection.evaluation.domain.valueobject.Money;
import com.frauddetection.shared.enums.EntityType;
import com.frauddetection.shared.enums.OperatorType;
import com.frauddetection.shared.exception.EntityPreprocessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DefaultPreprocessorTest {

    @InjectMocks
    private DefaultPreprocessor defaultPreprocessor;

    private Transaction transaction;

    @BeforeEach
    void setUp() {
        transaction = new Transaction(
                new Money(null, "EUR"), "123", "KFC", "80", "Copenhagen");
    }

    @Test
    void extractValue_terminalId_returnsValue() throws EntityPreprocessingException {
        FraudRule rule = buildRule(EntityType.TERMINAL_ID);

        String result = defaultPreprocessor.extractValue(transaction, rule);

        Assertions.assertEquals("123", result);
    }

    @Test
    void extractValue_city_returnsValue() throws EntityPreprocessingException {
        FraudRule rule = buildRule(EntityType.CITY);

        String result = defaultPreprocessor.extractValue(transaction, rule);

        Assertions.assertEquals("Copenhagen", result);
    }

    @Test
    void extractValue_unknownEntityType_throwsException() {
        FraudRule rule = buildRule(EntityType.COUNTRY);

        Assertions.assertThrows(EntityPreprocessingException.class,
                () -> defaultPreprocessor.extractValue(transaction, rule));
    }

    @Test
    void extractValue_nullTransaction_throwsException() {
        FraudRule rule = buildRule(EntityType.TERMINAL_ID);

        Assertions.assertThrows(EntityPreprocessingException.class,
                () -> defaultPreprocessor.extractValue(null, rule));
    }

    private FraudRule buildRule(EntityType entityType) {
        return new FraudRule("test-rule", "KFC", "OPERATIONAL", entityType,
                OperatorType.EQUAL, 30.0, null, "value", null, null, null);
    }
}
