package com.frauddetection.evaluation.adapter.in.rest;

import com.frauddetection.evaluation.domain.model.Transaction;
import com.frauddetection.evaluation.domain.valueobject.EvaluationResult;
import com.frauddetection.evaluation.domain.valueobject.Money;

public final class TransactionMapper {

    private TransactionMapper() {
    }

    public static Transaction toDomain(TransactionRequest request) {
        return new Transaction(
                new Money(request.amount(), request.currency()),
                request.terminalId(),
                request.merchant(),
                request.terminalThreatScore(),
                request.city());
    }

    public static EvaluationResponse toResponse(EvaluationResult result) {
        return new EvaluationResponse(
                result.riskLevel().getCode(),
                result.riskLevel().getLabel(),
                String.valueOf(result.fraudScore()),
                null);
    }
}
