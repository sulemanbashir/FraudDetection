package com.frauddetection.rules.operational.service.operatordatype;

import java.text.ParseException;

import com.frauddetection.rules.operational.exception.InvalidInfoException;
import org.springframework.stereotype.Component;

@Component
public class EqualOperatorString implements OperatorDatatype {

	@Override
	public boolean applyOpertion(String operand1, String operand2) throws ParseException, InvalidInfoException {
		return operand1.equals(operand2);
	}

}
