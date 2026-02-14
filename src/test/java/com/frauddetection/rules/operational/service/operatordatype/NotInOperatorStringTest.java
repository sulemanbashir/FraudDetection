package com.frauddetection.rules.operational.service.operatordatype;

import java.text.ParseException;

import com.frauddetection.exception.InvalidInfoException;
import com.frauddetection.service.operator.impl.NotInOperatorString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class NotInOperatorStringTest {

    @InjectMocks
    NotInOperatorString notInOperatorString;

    @Test
    public void notInOperatorString_whenOperand1NotExistInOperand2() throws ParseException, InvalidInfoException {
        boolean result = notInOperatorString.applyOperation("44", "33,,66,55");
        Assertions.assertTrue(result);
    }

    @Test
    public void notInOperatorString_whenOperand1ExistInOperand2() throws ParseException, InvalidInfoException {
        boolean result = notInOperatorString.applyOperation("44", "33,44,66,55");
        Assertions.assertFalse(result);
    }

}
