package com.frauddetection.rules.operational.service.operators;

import java.text.ParseException;

import com.frauddetection.rules.operational.exception.InvalidInfoException;
import org.springframework.stereotype.Component;

import com.frauddetection.model.FraudParameterDetails;

@Component
public interface Operator {
	
	boolean applyOpertion(FraudParameterDetails fraudRule) throws ParseException, InvalidInfoException;
}
