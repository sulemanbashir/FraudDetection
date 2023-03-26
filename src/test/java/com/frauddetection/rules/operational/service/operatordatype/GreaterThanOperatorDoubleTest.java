package com.frauddetection.rules.operational.service.operatordatype;

import java.text.ParseException;

import com.frauddetection.rules.operational.exception.InvalidInfoException;
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
	public void greaterThanOperatorDoubleTest_whenOperand1GreaterThanOperand2() {
		
		boolean result;
		try {
			result = greaterThanOperatorDouble.applyOpertion("44", "33");
			Assertions.assertEquals(true, result);
		} catch (ParseException | InvalidInfoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void greaterThanOperatorDoubleTest_whenOperand1NotGreaterThanOperand2() {
		
		boolean result;
		try {
			result = greaterThanOperatorDouble.applyOpertion("33", "55");
			Assertions.assertEquals(false, result);
		} catch (ParseException | InvalidInfoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
