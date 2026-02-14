package com.frauddetection.service.fraud.operational;

import java.text.ParseException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.frauddetection.dto.ResponseInfo;
import com.frauddetection.dto.TransactionInfo;
import com.frauddetection.exception.InvalidInfoException;
import com.frauddetection.model.FraudParameterDetails;
import com.frauddetection.service.fraud.FraudRuleEvaluator;
import com.frauddetection.service.fraud.FraudScoreEvaluatorService;
import com.frauddetection.service.operator.OperatorDatatype;
import com.frauddetection.service.operator.OperatorDatatypeService;

@Component
public class OperationalFraudEvaluatorImpl implements FraudRuleEvaluator {
    private static final Logger log = LogManager.getLogger(OperationalFraudEvaluatorImpl.class);

    private final FraudScoreEvaluatorService fraudScoreEvaluatorService;
    private final OperatorDatatypeService operatorDatatypeService;

    public OperationalFraudEvaluatorImpl(
            FraudScoreEvaluatorService fraudScoreEvaluatorService,
            OperatorDatatypeService operatorDatatypeService) {
        this.fraudScoreEvaluatorService = fraudScoreEvaluatorService;
        this.operatorDatatypeService = operatorDatatypeService;
    }

    @Override
    public ResponseInfo applyFraudRules(TransactionInfo transactionInfo, FraudParameterDetails fraudRule) throws ParseException, InvalidInfoException {

        log.info("Going to apply operational fraud rule with operatorId[" + fraudRule.getOperator().getId() + "]");
        ResponseInfo responseInfo = ResponseInfo.empty();

        OperatorDatatype operator = operatorDatatypeService.getOperatorByDatabaseValues(
                fraudRule.getOperator().getName(),
                fraudRule.getEntity().getDatatype().getName());

        boolean isFraudRuleHit = operator.applyOperation(
                fraudRule.getActualCriteriaValue(),
                fraudRule.getFraudCriteriaValue1());

        if (isFraudRuleHit) {
            log.info("Fraud Rule Hit. Going to evaluate Fraud Score");
            responseInfo = fraudScoreEvaluatorService.evaluateFraudScore(fraudRule.getFraudScore());
            log.info("Response Received After Fraud Evaluation: {} for fraud rule:[{}]", responseInfo, fraudRule.getFraudRuleName());
        }
        return responseInfo;
    }

}
