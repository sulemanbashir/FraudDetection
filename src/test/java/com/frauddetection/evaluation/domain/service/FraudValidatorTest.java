package com.frauddetection.evaluation.domain.service;

import com.frauddetection.evaluation.domain.model.FraudRule;
import com.frauddetection.evaluation.domain.model.Transaction;
import com.frauddetection.shared.enums.EntityType;
import com.frauddetection.shared.enums.OperatorType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class FraudValidatorTest {

    @InjectMocks
    FraudValidator fraudValidator;

    @Test
    public void shouldEvaluate_whenTerminalScoreNotGivenInRequest() {
        FraudRule rule = buildRule(40);
        Transaction transaction = new Transaction(null, null, null, null, null);

        boolean result = fraudValidator.shouldEvaluate(rule, transaction);

        Assertions.assertTrue(result);
    }

    @Test
    public void shouldEvaluate_whenThresholdNotConfiguredInRule() {
        FraudRule rule = buildRule(null);
        Transaction transaction = new Transaction(null, null, null, "50", null);

        boolean result = fraudValidator.shouldEvaluate(rule, transaction);

        Assertions.assertTrue(result);
    }

    @Test
    public void shouldEvaluate_whenRequestScoreExceedsThreshold() {
        FraudRule rule = buildRule(40);
        Transaction transaction = new Transaction(null, null, null, "50", null);

        boolean result = fraudValidator.shouldEvaluate(rule, transaction);

        Assertions.assertTrue(result);
    }

    @Test
    public void shouldNotEvaluate_whenRequestScoreBelowThreshold() {
        FraudRule rule = buildRule(40);
        Transaction transaction = new Transaction(null, null, null, "30", null);

        boolean result = fraudValidator.shouldEvaluate(rule, transaction);

        Assertions.assertFalse(result);
    }

    private FraudRule buildRule(Integer terminalThreatThreshold) {
        return new FraudRule("test-rule", "KFC", "OPERATIONAL",
                EntityType.AMOUNT, OperatorType.GREATER_THAN, 40.0,
                terminalThreatThreshold, "500", null, null, null);
    }
}
