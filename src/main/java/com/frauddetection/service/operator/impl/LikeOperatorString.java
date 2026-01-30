package com.frauddetection.service.operator.impl;

import java.text.ParseException;

import com.frauddetection.exception.InvalidInfoException;
import com.frauddetection.service.operator.OperatorDatatype;
import org.springframework.stereotype.Component;

@Component
public class LikeOperatorString implements OperatorDatatype {

    @Override
    public boolean applyOperation(String operand1, String operand2) throws ParseException, InvalidInfoException {
        if (operand1 == null || operand2 == null) {
            throw new IllegalArgumentException("Operands cannot be null for LIKE comparison");
        }

        return operand1.contains(operand2);
    }

}
