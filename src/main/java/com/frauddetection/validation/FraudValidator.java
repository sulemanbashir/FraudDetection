package com.frauddetection.validation;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.frauddetection.dto.TransactionInfo;
import com.frauddetection.model.FraudParameterDetails;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class FraudValidator {

	@Autowired
	private Validator validator;

	public boolean validateFraudScore(FraudParameterDetails fraudParameterDetails,
			TransactionInfo transactionInfo) {

		log.info("Going to validate terminal thread score");
		Set<ConstraintViolation<FraudParameterDetails>> definedTerminalScoreValidation = validator
				.validate(fraudParameterDetails, TerminalThreadScoreValidator.class);

		Set<ConstraintViolation<TransactionInfo>> requestTerminalScoreValidation = validator.validate(transactionInfo,
				TerminalThreadScoreValidator.class);

		if (!definedTerminalScoreValidation.isEmpty() || !requestTerminalScoreValidation.isEmpty()) {
			log.info("Returing true because no thread score found in request or fraud rule");
			return true;
		}

		if (definedTerminalScoreValidation.isEmpty() && requestTerminalScoreValidation.isEmpty() && Integer.parseInt(transactionInfo.getTerminalThreatScore()) >= fraudParameterDetails.getTerminalThreadThreshold()) {
			log.info("Returing true because rule thread score threshold exceed");
			return true;
		}
		log.info("Returing false because no need to evaluate fraud rule as the threshold limit not exceed!");
		return false;
	}

}
