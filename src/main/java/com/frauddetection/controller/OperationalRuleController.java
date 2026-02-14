package com.frauddetection.controller;

import com.frauddetection.service.fraud.operational.OperationalRuleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.frauddetection.dto.FraudParameterDetailsDto;
import com.frauddetection.dto.ResponseInfo;
import com.frauddetection.validation.OperationalRuleValidator;

@RestController
@RequestMapping("/operationalrules")
@Tag(name = "Fraud Rule Management", description = "CRUD operations for operational fraud rules")
public class OperationalRuleController {

    private final OperationalRuleService operationalRuleService;
    private final OperationalRuleValidator operationalRuleValidator;

    public OperationalRuleController(OperationalRuleService operationalRuleService,
                                     OperationalRuleValidator operationalRuleValidator) {
        this.operationalRuleService = operationalRuleService;
        this.operationalRuleValidator = operationalRuleValidator;
    }

    @PostMapping("/addFraudRule")
    @Operation(summary = "Add a fraud rule", description = "Creates a new operational fraud rule for a merchant")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Rule created"),
            @ApiResponse(responseCode = "400", description = "Validation error")
    })
    public ResponseEntity<ResponseInfo> addFraudRule(@RequestBody FraudParameterDetailsDto fraudParameterDetailsDto) {

        ResponseInfo validationErrorResponse = operationalRuleValidator.validateInsertOperationalRuleDto(fraudParameterDetailsDto);

        if (!validationErrorResponse.violationErrorResponse().violations().isEmpty()) {
            return ResponseEntity.badRequest().body(validationErrorResponse);
        }

        operationalRuleService.saveFraudRule(fraudParameterDetailsDto);
        return ResponseEntity.ok().body(ResponseInfo.success("Success"));

    }

    @PostMapping("/updateFraudRule")
    @Operation(summary = "Update a fraud rule", description = "Updates an existing operational fraud rule by merchant and rule name")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Rule updated"),
            @ApiResponse(responseCode = "400", description = "Validation error")
    })
    public ResponseEntity<ResponseInfo> updateFraudRule(@RequestBody FraudParameterDetailsDto fraudParameterDetailsDto) {

        ResponseInfo validationErrorResponse = operationalRuleValidator.validateUpdateOperationalRuleDto(fraudParameterDetailsDto);

        if (!validationErrorResponse.violationErrorResponse().violations().isEmpty()) {
            return ResponseEntity.badRequest().body(validationErrorResponse);
        }

        operationalRuleService.updateFraudRule(fraudParameterDetailsDto);
        return ResponseEntity.ok().body(ResponseInfo.success("Success"));

    }

    @PostMapping("/deleteFraudRule")
    @Operation(summary = "Delete a fraud rule", description = "Deletes an operational fraud rule by merchant and rule name")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Rule deleted"),
            @ApiResponse(responseCode = "400", description = "Validation error")
    })
    public ResponseEntity<ResponseInfo> deleteFraudRule(@RequestBody FraudParameterDetailsDto fraudParameterDetailsDto) {

        ResponseInfo validationErrorResponse = operationalRuleValidator.validateUpdateOperationalRuleDto(fraudParameterDetailsDto);

        if (!validationErrorResponse.violationErrorResponse().violations().isEmpty()) {
            return ResponseEntity.badRequest().body(validationErrorResponse);
        }

        operationalRuleService.deleteFraudRule(fraudParameterDetailsDto);
        return ResponseEntity.ok().body(ResponseInfo.success("Success"));

    }

}
