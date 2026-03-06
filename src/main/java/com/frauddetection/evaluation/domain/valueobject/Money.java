package com.frauddetection.evaluation.domain.valueobject;

public record Money(Double amount, String currency) {

    public Money {
        if (amount != null && amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative: " + amount);
        }
    }
}
