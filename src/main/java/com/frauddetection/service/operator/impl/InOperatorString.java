package com.frauddetection.service.operator.impl;

import java.text.ParseException;
import java.util.List;

import com.frauddetection.exception.InvalidInfoException;
import com.frauddetection.service.operator.OperatorDatatype;
import org.springframework.stereotype.Component;

import com.frauddetection.utils.CommonUtils;
import com.frauddetection.utils.CriteriaRuleConstants;

@Component
public class InOperatorString implements OperatorDatatype {

    @Override
    public boolean applyOperation(String operand1, String operand2) throws ParseException, InvalidInfoException {
        if (operand1 == null || operand2 == null) {
            throw new IllegalArgumentException("Operands cannot be null for IN comparison");
        }

        List<String> operands = CommonUtils.getTokenizedStringData(operand2, CriteriaRuleConstants.DELIM_CSV);

        if (operands == null || operands.isEmpty()) {
            throw new IllegalArgumentException(
                    String.format("operand2 must be a valid CSV list for IN comparison. operand2='%s'", operand2));
        }

        return operands.contains(operand1);
    }
}
