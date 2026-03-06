package com.frauddetection.evaluation.application;

import java.text.ParseException;
import java.util.Collections;
import java.util.List;

import com.frauddetection.evaluation.domain.model.FraudRule;
import com.frauddetection.evaluation.domain.model.ScoreLevel;
import com.frauddetection.evaluation.domain.model.Transaction;
import com.frauddetection.evaluation.domain.valueobject.EvaluationResult;
import com.frauddetection.evaluation.domain.valueobject.Money;
import com.frauddetection.evaluation.domain.valueobject.RiskLevel;
import com.frauddetection.shared.enums.EntityType;
import com.frauddetection.shared.enums.OperatorType;
import com.frauddetection.shared.exception.EntityPreprocessingException;
import com.frauddetection.shared.exception.InvalidInfoException;
import com.frauddetection.evaluation.domain.service.FraudRuleEvaluator;
import com.frauddetection.evaluation.domain.service.FraudValidator;
import com.frauddetection.evaluation.domain.service.preprocessing.EntityPreprocessor;
import com.frauddetection.evaluation.domain.service.preprocessing.EntityPreprocessorService;
import com.frauddetection.evaluation.port.out.DomainEventPublisher;
import com.frauddetection.evaluation.port.out.FraudRuleProvider;
import com.frauddetection.evaluation.port.out.ScoreLevelProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FraudEvaluationServiceTest {

    @Mock
    private FraudRuleProvider fraudRuleProvider;

    @Mock
    private FraudValidator fraudValidator;

    @Mock
    private EntityPreprocessorService entityPreprocessorService;

    @Mock
    private FraudRuleEvaluator fraudRuleEvaluator;

    @Mock
    private ScoreLevelProvider scoreLevelProvider;

    @Mock
    private DomainEventPublisher domainEventPublisher;

    @InjectMocks
    private FraudEvaluationService fraudEvaluationService;

    private Transaction transaction;
    private static final List<ScoreLevel> SCORE_LEVELS = List.of(
            new ScoreLevel("L", "Low", 0, 39),
            new ScoreLevel("M", "Medium", 40, 59),
            new ScoreLevel("H", "High", 60, 100)
    );

    @BeforeEach
    void setUp() {
        transaction = new Transaction(
                new Money(600.0, "EUR"), "123", "KFC", "80", null);
    }

    @Test
    void evaluate_withNoRules_returnsLowScore() throws ParseException, InvalidInfoException, EntityPreprocessingException {
        when(fraudRuleProvider.findRulesByMerchant("KFC"))
                .thenReturn(Collections.emptyList());
        when(scoreLevelProvider.getScoreLevels())
                .thenReturn(SCORE_LEVELS);

        EvaluationResult result = fraudEvaluationService.evaluate(transaction);

        Assertions.assertEquals(RiskLevel.LOW, result.riskLevel());
        Assertions.assertEquals(0.0, result.fraudScore());
        verify(fraudValidator, never()).shouldEvaluate(any(), any());
    }

    @Test
    void evaluate_withMatchingRule_returnsCorrectScore() throws ParseException, InvalidInfoException, EntityPreprocessingException {
        FraudRule rule = buildFraudRule();

        when(fraudRuleProvider.findRulesByMerchant("KFC"))
                .thenReturn(List.of(rule));
        when(fraudValidator.shouldEvaluate(rule, transaction))
                .thenReturn(true);

        EntityPreprocessor preprocessor = mock(EntityPreprocessor.class);
        when(entityPreprocessorService.getPreprocessor(EntityType.AMOUNT))
                .thenReturn(preprocessor);
        when(preprocessor.extractValue(transaction, rule))
                .thenReturn("600.0");
        when(fraudRuleEvaluator.evaluateRule(transaction, rule, "600.0"))
                .thenReturn(40.0);
        when(scoreLevelProvider.getScoreLevels())
                .thenReturn(SCORE_LEVELS);

        EvaluationResult result = fraudEvaluationService.evaluate(transaction);

        Assertions.assertEquals(RiskLevel.MEDIUM, result.riskLevel());
        Assertions.assertEquals(40.0, result.fraudScore());
        verify(preprocessor).extractValue(transaction, rule);
    }

    @Test
    void evaluate_whenThresholdNotMet_skipsRule() throws ParseException, InvalidInfoException, EntityPreprocessingException {
        FraudRule rule = buildFraudRule();

        when(fraudRuleProvider.findRulesByMerchant("KFC"))
                .thenReturn(List.of(rule));
        when(fraudValidator.shouldEvaluate(rule, transaction))
                .thenReturn(false);
        when(scoreLevelProvider.getScoreLevels())
                .thenReturn(SCORE_LEVELS);

        EvaluationResult result = fraudEvaluationService.evaluate(transaction);

        Assertions.assertEquals(RiskLevel.LOW, result.riskLevel());
        verify(fraudRuleEvaluator, never()).evaluateRule(any(), any(), anyString());
        verify(entityPreprocessorService, never()).getPreprocessor(any());
    }

    private FraudRule buildFraudRule() {
        return new FraudRule("amount-check", "KFC", "OPERATIONAL",
                EntityType.AMOUNT, OperatorType.GREATER_THAN, 40.0,
                null, "500", null, null, null);
    }
}
