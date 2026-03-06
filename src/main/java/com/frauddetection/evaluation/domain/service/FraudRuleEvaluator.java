package com.frauddetection.evaluation.domain.service;

import java.text.ParseException;

import com.frauddetection.evaluation.domain.model.FraudRule;
import com.frauddetection.evaluation.domain.model.Transaction;
import com.frauddetection.shared.exception.InvalidInfoException;

public interface FraudRuleEvaluator {

    double evaluateRule(Transaction transaction, FraudRule rule, String actualValue)
            throws ParseException, InvalidInfoException;
}
