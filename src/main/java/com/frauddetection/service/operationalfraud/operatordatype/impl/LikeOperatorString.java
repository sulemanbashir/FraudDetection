package com.frauddetection.service.operationalfraud.operatordatype.impl;

import java.text.ParseException;

import com.frauddetection.exception.InvalidInfoException;
import com.frauddetection.service.operationalfraud.OperatorDatatype;
import org.springframework.stereotype.Component;

@Component
public class LikeOperatorString implements OperatorDatatype {

	@Override
	public boolean applyOperation(String operand1, String operand2) throws ParseException, InvalidInfoException {
		return operand1.contains(operand2);
	}

}
