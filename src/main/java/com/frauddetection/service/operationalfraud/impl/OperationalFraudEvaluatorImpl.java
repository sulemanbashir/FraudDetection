package com.frauddetection.service.operationalfraud.impl;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.frauddetection.dto.ResponseInfo;
import com.frauddetection.dto.TransactionInfo;
import com.frauddetection.enums.factory.OperatorDatatypeFactory;
import com.frauddetection.exception.InvalidInfoException;
import com.frauddetection.model.FraudParameterDetails;
import com.frauddetection.service.FraudScoreEvaluatorService;
import com.frauddetection.service.operationalfraud.OperationalFraudEvaluator;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class OperationalFraudEvaluatorImpl implements OperationalFraudEvaluator {

	@Autowired
	private FraudScoreEvaluatorService fraudScoreEvaluatorService;

	@Override
	public ResponseInfo applyFraudRules(TransactionInfo transactionInfo, FraudParameterDetails fraudRule) throws ParseException, InvalidInfoException {

		log.info("Going to apply operational fraud rule with operatorId[" + fraudRule.getOperator().getId() + "]");
		ResponseInfo responseInfo = new ResponseInfo();

		boolean isFraudRuleHit = OperatorDatatypeFactory.getOperatorDataTypeEnum(fraudRule.getOperator().getId(), fraudRule.getEntity().getDatatype().getId()).getOperatorDatatype().applyOperation(fraudRule.getActualCriteriaValue(),
				fraudRule.getFraudCriteriaValue1());
		if (isFraudRuleHit) {
			log.info("Fraud Rule Hit!!. Going to evaluate Fraud Score");
			responseInfo = fraudScoreEvaluatorService.evaluateFraudScore(fraudRule.getFraudScore());
			log.info("Response Received After Fraud Evaluation: " + responseInfo + "for fraud rule:[" + fraudRule.getFraudRuleName() + "]");
		}
		return responseInfo;
	}

}
