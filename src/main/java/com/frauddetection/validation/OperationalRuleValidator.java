package com.frauddetection.validation;

import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.frauddetection.dto.FraudParameterDetailsDto;
import com.frauddetection.dto.ResponseInfo;
import com.frauddetection.dto.ViolationErrorResponse;
import com.frauddetection.dto.ViolationDto;
import com.frauddetection.validation.groups.OperationalFraudRuleInsertValidation;
import com.frauddetection.validation.groups.OperationalFraudRuleUpdateValidation;

@Component
public class OperationalRuleValidator {

	@Autowired
	private Validator validator;

	public ResponseInfo validateInsertOperationalRuleDto(FraudParameterDetailsDto fraudParameterDetailsDto) {
		Set<ConstraintViolation<FraudParameterDetailsDto>> validation = validator.validate(fraudParameterDetailsDto, OperationalFraudRuleInsertValidation.class);
		return addViolations(validation);
	}

	public ResponseInfo validateUpdateOperationalRuleDto(FraudParameterDetailsDto fraudParameterDetailsDto) {
		Set<ConstraintViolation<FraudParameterDetailsDto>> validation = validator.validate(fraudParameterDetailsDto, OperationalFraudRuleUpdateValidation.class);
		return addViolations(validation);
	}

	private ResponseInfo addViolations(Set<ConstraintViolation<FraudParameterDetailsDto>> validation) {
		ResponseInfo responseInfo = new ResponseInfo("Bad Request", new ViolationErrorResponse());
		responseInfo.getViolationErrorResponse().getViolations().addAll(validation.stream().map(v -> new ViolationDto(v.getPropertyPath().toString(), v.getMessage())).collect(Collectors.toList()));
		return responseInfo;
	}
}
