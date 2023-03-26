package com.frauddetection.rules.operational.service.operatordatype;

import java.text.ParseException;
import java.util.List;

import com.frauddetection.rules.operational.exception.InvalidInfoException;
import com.frauddetection.utils.CommonUtils;
import com.frauddetection.utils.CriteriaRuleConstants;
import org.springframework.stereotype.Component;

@Component
public class InOperatorString implements OperatorDatatype {

	@Override
	public boolean applyOpertion(String operand1, String operand2) throws ParseException, InvalidInfoException {
		List<String> operands = null;
		operands = CommonUtils.getTokenizedStringData(operand2, CriteriaRuleConstants.DELIM_CSV);
		return operands.contains(operand1);
	}
}
