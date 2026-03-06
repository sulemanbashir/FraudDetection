package com.frauddetection.evaluation.domain.model;

import com.frauddetection.evaluation.domain.valueobject.Money;

public record Transaction(
        Money money,
        String terminalId,
        String merchant,
        String terminalThreatScore,
        String city) {
}
