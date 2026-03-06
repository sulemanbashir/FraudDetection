package com.frauddetection.evaluation.domain.service.operator.impl;

import java.text.ParseException;

import com.frauddetection.shared.exception.InvalidInfoException;
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
