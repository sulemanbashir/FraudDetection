package com.frauddetection.rules.operational.service.operatordatype;

import java.text.ParseException;

import com.frauddetection.exception.InvalidInfoException;
import com.frauddetection.service.operator.impl.InOperatorString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class InOperatorStringTest {

    @InjectMocks
    InOperatorString inOperatorString;

    @Test
    public void inOperatorStringTest_whenOperand1ExistInOperand2() throws ParseException, InvalidInfoException {
        boolean result = inOperatorString.applyOperation("44", "33,44,66,55");
        Assertions.assertTrue(result);
    }

    @Test
    public void inOperatorStringTest_whenOperand1NotExistInOperand2() throws ParseException, InvalidInfoException {
        boolean result = inOperatorString.applyOperation("44", "33,66,55");
        Assertions.assertFalse(result);
    }

}
