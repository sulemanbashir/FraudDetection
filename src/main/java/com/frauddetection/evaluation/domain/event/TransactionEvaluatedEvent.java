package com.frauddetection.evaluation.domain.event;

import com.frauddetection.shared.domain.event.DomainEvent;

public class TransactionEvaluatedEvent extends DomainEvent {

    private final String merchant;
    private final double totalFraudScore;
    private final String riskLevel;

    public TransactionEvaluatedEvent(String merchant, double totalFraudScore, String riskLevel) {
        super();
        this.merchant = merchant;
        this.totalFraudScore = totalFraudScore;
        this.riskLevel = riskLevel;
    }

    public String getMerchant() {
        return merchant;
    }

    public double getTotalFraudScore() {
        return totalFraudScore;
    }

    public String getRiskLevel() {
        return riskLevel;
    }
}
