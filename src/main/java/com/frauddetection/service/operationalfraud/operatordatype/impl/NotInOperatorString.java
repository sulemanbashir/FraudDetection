package com.frauddetection.service.operationalfraud.operatordatype.impl;

import java.text.ParseException;
import java.util.List;

import com.frauddetection.exception.InvalidInfoException;
import com.frauddetection.service.operationalfraud.OperatorDatatype;
import org.springframework.stereotype.Component;

import com.frauddetection.utils.CommonUtils;
import com.frauddetection.utils.CriteriaRuleConstants;

@Component
public class NotInOperatorString implements OperatorDatatype {

	@Override
	public boolean applyOperation(String operand1, String operand2) throws ParseException, InvalidInfoException {
		List<String> operands = null;
		operands = CommonUtils.getTokenizedStringData(operand2, CriteriaRuleConstants.DELIM_CSV);
		return !operands.contains(operand1);
	}
}
