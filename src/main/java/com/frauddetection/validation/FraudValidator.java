package com.frauddetection.validation;

import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import com.frauddetection.dto.TransactionInfo;
import com.frauddetection.model.FraudParameterDetails;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.frauddetection.validation.groups.TerminalThreatScoreValidator;

@Component
public class FraudValidator {
    private static final Logger log = LogManager.getLogger(FraudValidator.class);

    private final Validator validator;

    public FraudValidator(Validator validator) {
        this.validator = validator;
    }

    public boolean validateFraudScore(FraudParameterDetails fraudParameterDetails, TransactionInfo transactionInfo) {

        log.info("Going to validate terminal threat score");
        Set<ConstraintViolation<FraudParameterDetails>> definedTerminalScoreValidation = validator.validate(fraudParameterDetails, TerminalThreatScoreValidator.class);

        Set<ConstraintViolation<TransactionInfo>> requestTerminalScoreValidation = validator.validate(transactionInfo, TerminalThreatScoreValidator.class);

        if (!definedTerminalScoreValidation.isEmpty() || !requestTerminalScoreValidation.isEmpty()) {
            log.info("Returning true because no threat score found in request or fraud rule");
            return true;
        }

        if (definedTerminalScoreValidation.isEmpty() && requestTerminalScoreValidation.isEmpty() && Integer.parseInt(transactionInfo.terminalThreatScore()) >= fraudParameterDetails.getTerminalThreatThreshold()) {
            log.info("Returning true because rule threat score threshold exceeded");
            return true;
        }
        log.info("Returning false because threshold limit not exceeded, skipping fraud rule");
        return false;
    }

}
