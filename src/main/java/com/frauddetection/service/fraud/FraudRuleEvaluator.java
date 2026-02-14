package com.frauddetection.service.fraud;

import java.text.ParseException;

import com.frauddetection.dto.ResponseInfo;
import com.frauddetection.exception.InvalidInfoException;
import com.frauddetection.model.FraudParameterDetails;

import com.frauddetection.dto.TransactionInfo;

public interface FraudRuleEvaluator {

    ResponseInfo applyFraudRules(TransactionInfo transactionInfo, FraudParameterDetails fraudRule) throws ParseException, InvalidInfoException;

}
