package com.frauddetection.evaluation.domain.service;

import java.text.ParseException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.frauddetection.evaluation.domain.model.FraudRule;
import com.frauddetection.evaluation.domain.model.Transaction;
import com.frauddetection.shared.exception.InvalidInfoException;
import com.frauddetection.evaluation.domain.service.operator.OperatorDatatype;
import com.frauddetection.evaluation.domain.service.operator.OperatorDatatypeService;

@Component
public class OperationalFraudEvaluatorImpl implements FraudRuleEvaluator {
    private static final Logger log = LogManager.getLogger(OperationalFraudEvaluatorImpl.class);

    private final OperatorDatatypeService operatorDatatypeService;

    public OperationalFraudEvaluatorImpl(OperatorDatatypeService operatorDatatypeService) {
        this.operatorDatatypeService = operatorDatatypeService;
    }

    @Override
    public double evaluateRule(Transaction transaction, FraudRule rule, String actualValue)
            throws ParseException, InvalidInfoException {

        log.info("Applying fraud rule [{}] with operator [{}]", rule.name(), rule.operatorType());

        OperatorDatatype operator = operatorDatatypeService.getOperatorByDatabaseValues(
                rule.operatorType().getDatabaseValue(),
                rule.entityType().getDataType().getDatabaseValue());

        boolean isFraudRuleHit = operator.applyOperation(actualValue, rule.thresholdValue());

        if (isFraudRuleHit) {
            log.info("Fraud rule hit: {}, score: {}", rule.name(), rule.fraudScore());
            return rule.fraudScore();
        }

        return 0.0;
    }
}
