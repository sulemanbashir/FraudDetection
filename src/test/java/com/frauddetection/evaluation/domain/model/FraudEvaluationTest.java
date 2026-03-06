package com.frauddetection.evaluation.domain.model;

import java.util.List;

import com.frauddetection.evaluation.domain.aggregate.FraudEvaluation;
import com.frauddetection.evaluation.domain.valueobject.EvaluationResult;
import com.frauddetection.evaluation.domain.valueobject.Money;
import com.frauddetection.evaluation.domain.valueobject.RiskLevel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FraudEvaluationTest {

    private List<ScoreLevel> scoreLevels;

    @BeforeEach
    void setUp() {
        scoreLevels = List.of(
                new ScoreLevel("L", "Low", 0, 39),
                new ScoreLevel("M", "Medium", 40, 59),
                new ScoreLevel("H", "High", 60, 100)
        );
    }

    @Test
    void evaluateRiskLevel_lowScore_returnsLow() {
        FraudEvaluation evaluation = new FraudEvaluation("KFC", new Money(100.0, "EUR"));
        evaluation.addRuleScore("rule1", 20.0);

        EvaluationResult result = evaluation.evaluateRiskLevel(scoreLevels);

        Assertions.assertEquals(RiskLevel.LOW, result.riskLevel());
        Assertions.assertEquals(20.0, result.fraudScore());
    }

    @Test
    void evaluateRiskLevel_mediumScore_returnsMedium() {
        FraudEvaluation evaluation = new FraudEvaluation("KFC", new Money(100.0, "EUR"));
        evaluation.addRuleScore("rule1", 45.0);

        EvaluationResult result = evaluation.evaluateRiskLevel(scoreLevels);

        Assertions.assertEquals(RiskLevel.MEDIUM, result.riskLevel());
        Assertions.assertEquals(45.0, result.fraudScore());
    }

    @Test
    void evaluateRiskLevel_highScore_returnsHigh() {
        FraudEvaluation evaluation = new FraudEvaluation("KFC", new Money(100.0, "EUR"));
        evaluation.addRuleScore("rule1", 75.0);

        EvaluationResult result = evaluation.evaluateRiskLevel(scoreLevels);

        Assertions.assertEquals(RiskLevel.HIGH, result.riskLevel());
        Assertions.assertEquals(75.0, result.fraudScore());
    }

    @Test
    void evaluateRiskLevel_overMaxLimit_capsAtHigh() {
        FraudEvaluation evaluation = new FraudEvaluation("KFC", new Money(100.0, "EUR"));
        evaluation.addRuleScore("rule1", 80.0);
        evaluation.addRuleScore("rule2", 70.0);

        EvaluationResult result = evaluation.evaluateRiskLevel(scoreLevels);

        Assertions.assertEquals(RiskLevel.HIGH, result.riskLevel());
        Assertions.assertEquals(100.0, result.fraudScore());
    }

    @Test
    void evaluateRiskLevel_collectsViolations() {
        FraudEvaluation evaluation = new FraudEvaluation("KFC", new Money(100.0, "EUR"));
        evaluation.addRuleScore("rule1", 40.0);
        evaluation.addRuleScore("rule2", 30.0);

        EvaluationResult result = evaluation.evaluateRiskLevel(scoreLevels);

        Assertions.assertEquals(2, result.violations().size());
        Assertions.assertEquals("rule1", result.violations().get(0).ruleName());
        Assertions.assertEquals("rule2", result.violations().get(1).ruleName());
    }

    @Test
    void addRuleScore_zeroScore_doesNotAddViolation() {
        FraudEvaluation evaluation = new FraudEvaluation("KFC", new Money(100.0, "EUR"));
        evaluation.addRuleScore("rule1", 0.0);

        EvaluationResult result = evaluation.evaluateRiskLevel(scoreLevels);

        Assertions.assertEquals(0, result.violations().size());
        Assertions.assertEquals(0.0, result.fraudScore());
    }
}
