package com.frauddetection.controller;

import com.frauddetection.service.fraud.operational.OperationalRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.frauddetection.dto.FraudParameterDetailsDto;
import com.frauddetection.dto.ResponseInfo;
import com.frauddetection.validation.OperationalRuleValidator;

@RestController
@RequestMapping("/operationalrules")
public class OperationalRuleController {

    @Autowired
    OperationalRuleService operationalRuleService;

    @Autowired
    OperationalRuleValidator operationalRuleValidator;

    @PostMapping("/addFraudRule")
    @ResponseBody
    public ResponseEntity<ResponseInfo> addFraudRule(@RequestBody FraudParameterDetailsDto fraudParameterDetailsDto) {

        ResponseInfo validationErrorResponse = operationalRuleValidator.validateInsertOperationalRuleDto(fraudParameterDetailsDto);

        if (!validationErrorResponse.getViolationErrorResponse().getViolations().isEmpty()) {
            return ResponseEntity.badRequest().body(validationErrorResponse);
        }

        operationalRuleService.saveFraudRule(fraudParameterDetailsDto);
        return ResponseEntity.ok().body(new ResponseInfo("Success"));

    }

    @PostMapping("/updateFraudRule")
    @ResponseBody
    public ResponseEntity<ResponseInfo> updateFraudRule(@RequestBody FraudParameterDetailsDto fraudParameterDetailsDto) {

        ResponseInfo validationErrorResponse = operationalRuleValidator.validateUpdateOperationalRuleDto(fraudParameterDetailsDto);

        if (!validationErrorResponse.getViolationErrorResponse().getViolations().isEmpty()) {
            return ResponseEntity.badRequest().body(validationErrorResponse);
        }

        operationalRuleService.updateFraudRule(fraudParameterDetailsDto);
        return ResponseEntity.ok().body(new ResponseInfo("Success"));

    }

    @PostMapping("/deleteFraudRule")
    @ResponseBody
    public ResponseEntity<ResponseInfo> deleteFraudRule(@RequestBody FraudParameterDetailsDto fraudParameterDetailsDto) {

        ResponseInfo validationErrorResponse = operationalRuleValidator.validateUpdateOperationalRuleDto(fraudParameterDetailsDto);

        if (!validationErrorResponse.getViolationErrorResponse().getViolations().isEmpty()) {
            return ResponseEntity.badRequest().body(validationErrorResponse);
        }

        operationalRuleService.deleteFraudRule(fraudParameterDetailsDto);
        return ResponseEntity.ok().body(new ResponseInfo("Success"));

    }

}
