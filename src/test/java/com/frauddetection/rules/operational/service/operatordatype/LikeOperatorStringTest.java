package com.frauddetection.rules.operational.service.operatordatype;

import java.text.ParseException;

import com.frauddetection.exception.InvalidInfoException;
import com.frauddetection.service.operationalfraud.operatordatype.impl.LikeOperatorString;
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
	public void likeOperatorString_whenOperand1ContainsOperand2() {

		boolean result;
		try {
			result = likeOperatorString.applyOperation("1234455", "44");
			Assertions.assertEquals(true, result);
		} catch (ParseException | InvalidInfoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void likeOperatorString_whenOperand1NotContainsOperand2() {

		boolean result;
		try {
			result = likeOperatorString.applyOperation("223366", "44");
			Assertions.assertEquals(false, result);
		} catch (ParseException | InvalidInfoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
