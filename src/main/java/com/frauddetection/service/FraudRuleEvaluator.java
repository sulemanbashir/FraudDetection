package com.frauddetection.service;

import java.text.ParseException;

import org.springframework.stereotype.Component;

import com.frauddetection.dto.ResponseInfo;
import com.frauddetection.dto.TransactionInfo;
import com.frauddetection.model.FraudParameterDetails;
import com.frauddetection.rules.operational.exception.InvalidInfoException;

@Component
public interface FraudRuleEvaluator {
	
	public ResponseInfo applyFraudRules(TransactionInfo transactionInfo, FraudParameterDetails fraudRule) throws ParseException, InvalidInfoException;

}
