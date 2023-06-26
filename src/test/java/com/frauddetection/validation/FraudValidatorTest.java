package com.frauddetection.validation;

import javax.annotation.Resource;
import javax.validation.Validation;
import javax.validation.Validator;

import com.frauddetection.dto.TransactionInfo;
import com.frauddetection.model.FraudParameterDetails;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class FraudValidatorTest {

	@Spy
	Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	@InjectMocks
	@Resource
	FraudValidator fraudValidator;

	@Test
	public void terminalScoreValidation_whenNotGivenInRequest() {

		FraudParameterDetails fraudParameterDetails = new FraudParameterDetails();
		fraudParameterDetails.setTerminalThreadThreshold(40);
		TransactionInfo transactionInfo = new TransactionInfo();
		transactionInfo.setTerminalThreatScore(null);
		boolean result = fraudValidator.validateFraudScore(fraudParameterDetails, transactionInfo);
		Assertions.assertEquals(true, result);

	}

	@Test
	public void terminalScoreValidation_whenNotConfiguredInFraudRuleDefinition() {

		FraudParameterDetails fraudParameterDetails = new FraudParameterDetails();
		fraudParameterDetails.setTerminalThreadThreshold(null);
		TransactionInfo transactionInfo = new TransactionInfo();
		transactionInfo.setTerminalThreatScore("50");
		boolean result = fraudValidator.validateFraudScore(fraudParameterDetails, transactionInfo);
		Assertions.assertEquals(true, result);

	}

	@Test
	public void terminalScoreValidation_whenConfiguredThresholdExceedByRequestThreadScore() {

		FraudParameterDetails fraudParameterDetails = new FraudParameterDetails();
		fraudParameterDetails.setTerminalThreadThreshold(40);
		TransactionInfo transactionInfo = new TransactionInfo();
		transactionInfo.setTerminalThreatScore("50");
		boolean result = fraudValidator.validateFraudScore(fraudParameterDetails, transactionInfo);
		Assertions.assertEquals(true, result);

	}

	@Test
	public void terminalScoreValidation_whenConfiguredThresholdNotExceedByRequestThreadScore() {

		FraudParameterDetails fraudParameterDetails = new FraudParameterDetails();
		fraudParameterDetails.setTerminalThreadThreshold(40);
		TransactionInfo transactionInfo = new TransactionInfo();
		transactionInfo.setTerminalThreatScore("30");
		boolean result = fraudValidator.validateFraudScore(fraudParameterDetails, transactionInfo);
		Assertions.assertEquals(false, result);

	}

}
