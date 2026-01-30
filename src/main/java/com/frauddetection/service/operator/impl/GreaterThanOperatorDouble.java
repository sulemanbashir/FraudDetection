package com.frauddetection.service.operator.impl;

import java.text.ParseException;

import com.frauddetection.service.operator.OperatorDatatype;
import org.springframework.stereotype.Component;

import com.frauddetection.exception.InvalidInfoException;

@Component
public class GreaterThanOperatorDouble implements OperatorDatatype {

    @Override
    public boolean applyOperation(String operand1, String operand2) throws ParseException, InvalidInfoException {
        if (operand1 == null || operand2 == null) {
            throw new IllegalArgumentException("Operands cannot be null for GreaterThan comparison");
        }

        if (operand1.trim().isEmpty() || operand2.trim().isEmpty()) {
            throw new IllegalArgumentException("Operands cannot be empty for GreaterThan comparison");
        }

        try {
            double op1 = Double.parseDouble(operand1);
            double op2 = Double.parseDouble(operand2);
            return Double.compare(op1, op2) == 1;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    String.format("Operands must be valid numbers for GreaterThan comparison. operand1='%s', operand2='%s'",
                            operand1, operand2), e);
        }
    }

}
