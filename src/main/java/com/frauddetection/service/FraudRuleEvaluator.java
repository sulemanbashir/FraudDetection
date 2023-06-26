package com.frauddetection.service;

import java.text.ParseException;

import com.frauddetection.dto.ResponseInfo;
import com.frauddetection.exception.InvalidInfoException;
import com.frauddetection.model.FraudParameterDetails;
import org.springframework.stereotype.Component;

import com.frauddetection.dto.TransactionInfo;

@Component
public interface FraudRuleEvaluator {

	public ResponseInfo applyFraudRules(TransactionInfo transactionInfo, FraudParameterDetails fraudRule) throws ParseException, InvalidInfoException;

}
