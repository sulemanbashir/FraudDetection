package com.frauddetection.rules.operational.service.operatordatype;

import java.text.ParseException;

import com.frauddetection.exception.InvalidInfoException;
import com.frauddetection.service.operator.impl.GreaterThanOperatorDouble;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class GreaterThanOperatorDoubleTest {

    @InjectMocks
    GreaterThanOperatorDouble greaterThanOperatorDouble;

    @Test
    public void greaterThanOperatorDoubleTest_whenOperand1GreaterThanOperand2() throws ParseException, InvalidInfoException {
        boolean result = greaterThanOperatorDouble.applyOperation("44", "33");
        Assertions.assertTrue(result);
    }

    @Test
    public void greaterThanOperatorDoubleTest_whenOperand1NotGreaterThanOperand2() throws ParseException, InvalidInfoException {
        boolean result = greaterThanOperatorDouble.applyOperation("33", "55");
        Assertions.assertFalse(result);
    }

}
