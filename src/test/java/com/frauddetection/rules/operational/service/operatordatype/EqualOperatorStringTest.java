package com.frauddetection.rules.operational.service.operatordatype;

import java.text.ParseException;

import com.frauddetection.rules.operational.exception.InvalidInfoException;
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
	public void equalOperatorTest_whenBothOperandAreEqual() {
		
		boolean result;
		try {
			result = equalOperatorString.applyOpertion("abc", "abc");
			Assertions.assertEquals(true, result);
		} catch (ParseException | InvalidInfoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void equalOperatorTest_whenBothOperandAreNotEqual() {
		
		boolean result;
		try {
			result = equalOperatorString.applyOpertion("abc3", "abc");
			Assertions.assertEquals(false, result);
		} catch (ParseException | InvalidInfoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
