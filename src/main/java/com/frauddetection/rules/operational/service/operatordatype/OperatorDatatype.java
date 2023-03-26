package com.frauddetection.rules.operational.service.operatordatype;

import java.text.ParseException;

import com.frauddetection.rules.operational.exception.InvalidInfoException;
import org.springframework.stereotype.Component;

@Component
public interface OperatorDatatype {
	
	boolean applyOpertion(String operand1, String operand2) throws ParseException, InvalidInfoException;

}
