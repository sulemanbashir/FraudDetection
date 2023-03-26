package com.frauddetection.rules.operational.service.operatordatype;

import java.text.ParseException;

import com.frauddetection.rules.operational.exception.InvalidInfoException;
import org.springframework.stereotype.Component;

@Component
public class GreaterThanOperatorDouble implements OperatorDatatype {

	@Override
	public boolean applyOpertion(String operand1, String operand2) throws ParseException, InvalidInfoException {
		double op1 = Double.parseDouble(operand1);
		double op2 = Double.parseDouble(operand2);
		return Double.compare(op1, op2) == 1;
	}

}
