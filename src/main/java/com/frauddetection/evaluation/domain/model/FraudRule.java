package com.frauddetection.evaluation.domain.model;

import com.frauddetection.shared.enums.EntityType;
import com.frauddetection.shared.enums.OperatorType;

public record FraudRule(
        String name,
        String merchant,
        String fraudType,
        EntityType entityType,
        OperatorType operatorType,
        double fraudScore,
        Integer terminalThreatThreshold,
        String thresholdValue,
        String thresholdUpperValue,
        String exchangeCode,
        String toCurrencyCode) {
}
