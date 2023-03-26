package com.frauddetection.rules.operational.validation;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.frauddetection.rules.operational.dto.FraudParameterDetailsDto;
import com.frauddetection.rules.operational.dto.ValidationErrorResponseDto;
import com.frauddetection.rules.operational.dto.ViolationDto;

@Component
public class OperationalRuleValidator {

	@Autowired
	private Validator validator;

	public ValidationErrorResponseDto validateInsertOperationalRuleDto(FraudParameterDetailsDto fraudParameterDetailsDto) {
		ValidationErrorResponseDto errors = new ValidationErrorResponseDto();
		Set<ConstraintViolation<FraudParameterDetailsDto>> validation = validator.validate(fraudParameterDetailsDto, FraudRuleInsertValidation.class);

		if (!validation.isEmpty()) {
			validation.forEach(c -> {
				errors.getViolations().add(new ViolationDto(c.getPropertyPath().toString(), c.getMessage()));
			});
		}
		return errors;
	}

	public ValidationErrorResponseDto validateUpdateOperationalRuleDto(FraudParameterDetailsDto fraudParameterDetailsDto) {
		ValidationErrorResponseDto errors = new ValidationErrorResponseDto();
		Set<ConstraintViolation<FraudParameterDetailsDto>> validation = validator.validate(fraudParameterDetailsDto, FraudRuleUpdateValidation.class);

		if (!validation.isEmpty()) {
			validation.forEach(c -> {
				errors.getViolations().add(new ViolationDto(c.getPropertyPath().toString(), c.getMessage()));
			});
		}
		return errors;
	}
}
