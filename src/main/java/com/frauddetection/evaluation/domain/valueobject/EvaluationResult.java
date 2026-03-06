package com.frauddetection.evaluation.domain.valueobject;

import java.util.List;

public record EvaluationResult(RiskLevel riskLevel, double fraudScore, List<RuleViolation> violations) {
}
