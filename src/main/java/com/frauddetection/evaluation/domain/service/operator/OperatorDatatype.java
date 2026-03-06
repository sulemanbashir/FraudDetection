package com.frauddetection.evaluation.domain.service.operator;

import java.text.ParseException;

import org.springframework.stereotype.Component;

import com.frauddetection.shared.exception.InvalidInfoException;

@Component
public interface OperatorDatatype {
    boolean applyOperation(String operand1, String operand2) throws ParseException, InvalidInfoException;

}
