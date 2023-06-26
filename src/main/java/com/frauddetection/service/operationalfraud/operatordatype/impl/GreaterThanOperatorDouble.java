package com.frauddetection.service.operationalfraud.operatordatype.impl;

import java.text.ParseException;

import com.frauddetection.service.operationalfraud.OperatorDatatype;
import org.springframework.stereotype.Component;

import com.frauddetection.exception.InvalidInfoException;

@Component
public class GreaterThanOperatorDouble implements OperatorDatatype {

	@Override
	public boolean applyOperation(String operand1, String operand2) throws ParseException, InvalidInfoException {
		double op1 = Double.parseDouble(operand1);
		double op2 = Double.parseDouble(operand2);
		return Double.compare(op1, op2) == 1;
	}

}
