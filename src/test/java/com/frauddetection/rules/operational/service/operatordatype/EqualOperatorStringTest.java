package com.frauddetection.rules.operational.service.operatordatype;

import java.text.ParseException;

import com.frauddetection.exception.InvalidInfoException;
import com.frauddetection.service.operator.impl.EqualOperatorString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EqualOperatorStringTest {

    @InjectMocks
    EqualOperatorString equalOperatorString;

    @Test
    public void equalOperatorTest_whenBothOperandAreEqual() throws ParseException, InvalidInfoException {
        boolean result = equalOperatorString.applyOperation("abc", "abc");
        Assertions.assertTrue(result);
    }

    @Test
    public void equalOperatorTest_whenBothOperandAreNotEqual() throws ParseException, InvalidInfoException {
        boolean result = equalOperatorString.applyOperation("abc3", "abc");
        Assertions.assertFalse(result);
    }

}
