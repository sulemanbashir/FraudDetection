package com.frauddetection.evaluation.domain.event;

import com.frauddetection.shared.domain.event.DomainEvent;

public class FraudRuleViolatedEvent extends DomainEvent {

    private final String merchant;
    private final String ruleName;
    private final double ruleScore;

    public FraudRuleViolatedEvent(String merchant, String ruleName, double ruleScore) {
        super();
        this.merchant = merchant;
        this.ruleName = ruleName;
        this.ruleScore = ruleScore;
    }

    public String getMerchant() {
        return merchant;
    }

    public String getRuleName() {
        return ruleName;
    }

    public double getRuleScore() {
        return ruleScore;
    }
}
