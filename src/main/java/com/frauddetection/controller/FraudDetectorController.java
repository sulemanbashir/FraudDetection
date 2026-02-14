package com.frauddetection.controller;

import java.text.ParseException;

import com.frauddetection.exception.EntityPreprocessingException;
import com.frauddetection.exception.InvalidInfoException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.frauddetection.dto.ResponseInfo;
import com.frauddetection.dto.TransactionInfo;
import com.frauddetection.service.fraud.FraudDetectionService;

@RestController
@RequestMapping("/")
@Tag(name = "Fraud Evaluation", description = "Evaluate transactions against configured fraud rules")
public class FraudDetectorController {

    private final FraudDetectionService fraudDetectionService;

    public FraudDetectorController(FraudDetectionService fraudDetectionService) {
        this.fraudDetectionService = fraudDetectionService;
    }

    @PostMapping("evaluateFraud")
    @Operation(
            summary = "Evaluate a transaction for fraud",
            description = "Screens the transaction against all fraud rules configured for the given merchant and returns a risk score (Low/Medium/High)."
    )
    @ApiResponse(responseCode = "200", description = "Fraud evaluation result",
            content = @Content(schema = @Schema(implementation = ResponseInfo.class)))
    public ResponseInfo evaluateFraud(@RequestBody TransactionInfo transactionInfo)
            throws ParseException, InvalidInfoException, EntityPreprocessingException {
        return fraudDetectionService.evaluateFraud(transactionInfo);
    }

}
