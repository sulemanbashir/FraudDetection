package com.frauddetection.rules.operational.service.operatordatype;

import java.text.ParseException;

import com.frauddetection.exception.InvalidInfoException;
import com.frauddetection.service.operationalfraud.operatordatype.impl.NotInOperatorString;
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
    public void notInOperatorString_whenOperand1NotExisitInOperand2() {

        boolean result;
        try {
            result = notInOperatorString.applyOperation("44", "33,,66,55");
            Assertions.assertEquals(true, result);
        } catch (ParseException | InvalidInfoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void notInOperatorString_whenOperand1ExisitInOperand2() {

        boolean result;
        try {
            result = notInOperatorString.applyOperation("44", "33,44,66,55");
            Assertions.assertEquals(false, result);
        } catch (ParseException | InvalidInfoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
