package com.frauddetection.service.operator.impl;

import java.text.ParseException;

import com.frauddetection.exception.InvalidInfoException;
import com.frauddetection.service.operator.OperatorDatatype;
import org.springframework.stereotype.Component;

@Component
public class EqualOperatorString implements OperatorDatatype {

    @Override
    public boolean applyOperation(String operand1, String operand2) throws ParseException, InvalidInfoException {
        if (operand1 == null || operand2 == null) {
            throw new IllegalArgumentException("Operands cannot be null for Equal comparison");
        }
        return operand1.equals(operand2);
    }

}
