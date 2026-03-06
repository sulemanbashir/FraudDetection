package com.frauddetection.evaluation.domain.service;

import java.text.ParseException;

import com.frauddetection.evaluation.domain.model.FraudRule;
import com.frauddetection.evaluation.domain.model.Transaction;
import com.frauddetection.evaluation.domain.valueobject.Money;
import com.frauddetection.shared.enums.EntityType;
import com.frauddetection.shared.enums.OperatorType;
import com.frauddetection.shared.exception.InvalidInfoException;
import com.frauddetection.evaluation.domain.service.operator.OperatorDatatype;
import com.frauddetection.evaluation.domain.service.operator.OperatorDatatypeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OperationalFraudEvaluatorImplTest {

    @Mock
    private OperatorDatatypeService operatorDatatypeService;

    @InjectMocks
    private OperationalFraudEvaluatorImpl operationalFraudEvaluator;

    private Transaction transaction;
    private FraudRule fraudRule;
    private OperatorDatatype operatorDatatype;

    @BeforeEach
    void setUp() {
        transaction = new Transaction(
                new Money(600.0, "EUR"), null, "KFC", null, null);

        fraudRule = new FraudRule("city-check", "KFC", "OPERATIONAL",
                EntityType.CITY, OperatorType.EQUAL, 30.0,
                null, "Copenhagen", null, null, null);

        operatorDatatype = mock(OperatorDatatype.class);
    }

    @Test
    void evaluateRule_whenRuleHit_returnsScore() throws ParseException, InvalidInfoException {
        when(operatorDatatypeService.getOperatorByDatabaseValues("EQUAL", "STRING"))
                .thenReturn(operatorDatatype);
        when(operatorDatatype.applyOperation("Copenhagen", "Copenhagen"))
                .thenReturn(true);

        double score = operationalFraudEvaluator.evaluateRule(transaction, fraudRule, "Copenhagen");

        Assertions.assertEquals(30.0, score);
    }

    @Test
    void evaluateRule_whenRuleMiss_returnsZero() throws ParseException, InvalidInfoException {
        when(operatorDatatypeService.getOperatorByDatabaseValues("EQUAL", "STRING"))
                .thenReturn(operatorDatatype);
        when(operatorDatatype.applyOperation("Oslo", "Copenhagen"))
                .thenReturn(false);

        double score = operationalFraudEvaluator.evaluateRule(transaction, fraudRule, "Oslo");

        Assertions.assertEquals(0.0, score);
    }
}
