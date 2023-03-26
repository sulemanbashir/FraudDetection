package com.frauddetection.rules.operational.service.impl;

import java.text.ParseException;

import com.frauddetection.rules.operational.exception.InvalidInfoException;
import com.frauddetection.service.FraudScoreEvaluatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.frauddetection.dto.ResponseInfo;
import com.frauddetection.dto.TransactionInfo;
import com.frauddetection.model.FraudParameterDetails;
import com.frauddetection.rules.operational.factory.enums.OperatorFactory;
import com.frauddetection.rules.operational.service.OperationalFraudEvaluator;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class OperationalFraudEvaluatorImpl implements OperationalFraudEvaluator{

	@Autowired
	private FraudScoreEvaluatorService fraudScoreEvaluatorService;
	
	@Override
	public ResponseInfo applyFraudRules(TransactionInfo transactionInfo, FraudParameterDetails fraudRule) throws ParseException, InvalidInfoException {
		

		log.info("Going to apply operational fraud rule with operatorId[" + fraudRule.getOperator().getId() +"]");
		ResponseInfo responseInfo = new ResponseInfo();
		
		boolean isFraudRuleHit = OperatorFactory.getOperatorEnum(fraudRule.getOperator().getId())
												.createOperatorOpject()
												.applyOpertion(fraudRule);
		if(isFraudRuleHit) {
			log.info("Fraud Rule Hit!!. Going to evaluate Fraud Score");
			responseInfo = fraudScoreEvaluatorService.evaluateFraudScore(fraudRule.getFraudScore());
			log.info("Reponse Received After Fraud Evaluation: " +responseInfo+"for fraud rule:["+fraudRule.getFraudRuleName()+"]");
		}
		return responseInfo;
	}

}
