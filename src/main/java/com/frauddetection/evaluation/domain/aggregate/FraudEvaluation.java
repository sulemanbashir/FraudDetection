package com.frauddetection.evaluation.domain.aggregate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.frauddetection.evaluation.domain.event.FraudRuleViolatedEvent;
import com.frauddetection.evaluation.domain.event.TransactionEvaluatedEvent;
import com.frauddetection.evaluation.domain.model.ScoreLevel;
import com.frauddetection.evaluation.domain.valueobject.EvaluationResult;
import com.frauddetection.evaluation.domain.valueobject.Money;
import com.frauddetection.evaluation.domain.valueobject.RiskLevel;
import com.frauddetection.evaluation.domain.valueobject.RuleViolation;
import com.frauddetection.shared.domain.event.DomainEvent;

public class FraudEvaluation {

    private static final double MAX_SCORE = 100.0;

    private final String merchant;
    private final Money transactionMoney;
    private double totalScore;
    private final List<RuleViolation> violations;
    private final List<DomainEvent> domainEvents;

    public FraudEvaluation(String merchant, Money transactionMoney) {
        this.merchant = merchant;
        this.transactionMoney = transactionMoney;
        this.totalScore = 0.0;
        this.violations = new ArrayList<>();
        this.domainEvents = new ArrayList<>();
    }

    public void addRuleScore(String ruleName, double score) {
        if (score > 0) {
            this.violations.add(new RuleViolation(ruleName, score));
            this.domainEvents.add(new FraudRuleViolatedEvent(merchant, ruleName, score));
        }
        this.totalScore += score;
    }

    public EvaluationResult evaluateRiskLevel(List<ScoreLevel> scoreLevels) {
        double cappedScore = Math.min(totalScore, MAX_SCORE);

        if (totalScore > MAX_SCORE) {
            domainEvents.add(new TransactionEvaluatedEvent(merchant, MAX_SCORE, RiskLevel.HIGH.getCode()));
            return new EvaluationResult(RiskLevel.HIGH, MAX_SCORE, Collections.unmodifiableList(violations));
        }

        for (ScoreLevel level : scoreLevels) {
            if (cappedScore >= level.minRange() && cappedScore <= level.maxRange()) {
                RiskLevel riskLevel = RiskLevel.fromCode(level.code());
                domainEvents.add(new TransactionEvaluatedEvent(merchant, cappedScore, riskLevel.getCode()));
                return new EvaluationResult(riskLevel, cappedScore, Collections.unmodifiableList(violations));
            }
        }

        domainEvents.add(new TransactionEvaluatedEvent(merchant, cappedScore, RiskLevel.LOW.getCode()));
        return new EvaluationResult(RiskLevel.LOW, cappedScore, Collections.unmodifiableList(violations));
    }

    public List<DomainEvent> getDomainEvents() {
        return Collections.unmodifiableList(domainEvents);
    }

    public void clearDomainEvents() {
        domainEvents.clear();
    }

    public double getTotalScore() {
        return totalScore;
    }

    public String getMerchant() {
        return merchant;
    }

    public Money getTransactionMoney() {
        return transactionMoney;
    }

    public List<RuleViolation> getViolations() {
        return Collections.unmodifiableList(violations);
    }
}
