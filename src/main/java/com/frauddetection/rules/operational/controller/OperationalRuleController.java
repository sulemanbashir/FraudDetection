package com.frauddetection.rules.operational.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.frauddetection.model.FraudParameterDetails;
import com.frauddetection.rules.operational.dto.FraudParameterDetailsDto;
import com.frauddetection.rules.operational.dto.ValidationErrorResponseDto;
import com.frauddetection.rules.operational.service.OperationalRuleService;
import com.frauddetection.rules.operational.validation.OperationalRuleValidator;

@RestController
@RequestMapping("/operationalrules")
public class OperationalRuleController {

	@Autowired
	OperationalRuleService operationalRuleService;

	@Autowired
	OperationalRuleValidator operationalRuleValidator;

	@PostMapping("/addFraudRule")
	@ResponseBody
	public ResponseEntity<ValidationErrorResponseDto> addFraudRule(@RequestBody FraudParameterDetailsDto fraudParameterDetailsDto) {

		ValidationErrorResponseDto validationErrorResponseDto = operationalRuleValidator.validateInsertOperationalRuleDto(fraudParameterDetailsDto);

		if (!validationErrorResponseDto.getViolations().isEmpty()) {
			return ResponseEntity.badRequest().body(validationErrorResponseDto);
		}

		operationalRuleService.saveFraudRule(fraudParameterDetailsDto);
		return ResponseEntity.ok().body(null);
	}

	@PostMapping("/updateFraudRule")
	@ResponseBody
	public ResponseEntity<ValidationErrorResponseDto> updateFraudRule(@RequestBody FraudParameterDetailsDto fraudParameterDetailsDto) {
		ValidationErrorResponseDto validationErrorResponseDto = operationalRuleValidator.validateUpdateOperationalRuleDto(fraudParameterDetailsDto);

		if (!validationErrorResponseDto.getViolations().isEmpty()) {
			return ResponseEntity.badRequest().body(validationErrorResponseDto);
		}

		operationalRuleService.updateFraudRule(fraudParameterDetailsDto);
		return ResponseEntity.ok().body(null);
	}

	@PostMapping("/deleteFraudRule")
	@ResponseBody
	public FraudParameterDetails deleteFraudRule(@RequestBody FraudParameterDetails fraudParameterDetails) {
		return null;
	}

}
