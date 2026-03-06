package com.frauddetection.evaluation.domain.service.operator.impl;

import java.text.ParseException;

import com.frauddetection.shared.exception.InvalidInfoException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LikeOperatorStringTest {

    @InjectMocks
    LikeOperatorString likeOperatorString;

    @Test
    public void likeOperatorString_whenOperand1ContainsOperand2() throws ParseException, InvalidInfoException {
        boolean result = likeOperatorString.applyOperation("1234455", "44");
        Assertions.assertTrue(result);
    }

    @Test
    public void likeOperatorString_whenOperand1NotContainsOperand2() throws ParseException, InvalidInfoException {
        boolean result = likeOperatorString.applyOperation("223366", "44");
        Assertions.assertFalse(result);
    }

}
