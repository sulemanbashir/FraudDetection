package com.frauddetection.evaluation.adapter.in.rest;

import java.text.ParseException;

import com.frauddetection.evaluation.domain.model.Transaction;
import com.frauddetection.evaluation.domain.valueobject.EvaluationResult;
import com.frauddetection.shared.exception.EntityPreprocessingException;
import com.frauddetection.shared.exception.InvalidInfoException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.frauddetection.evaluation.port.in.EvaluateTransactionUseCase;

@RestController
@RequestMapping("/")
@Tag(name = "Fraud Evaluation", description = "Evaluate transactions against configured fraud rules")
public class FraudDetectorController {

    private final EvaluateTransactionUseCase evaluateTransactionUseCase;

    public FraudDetectorController(EvaluateTransactionUseCase evaluateTransactionUseCase) {
        this.evaluateTransactionUseCase = evaluateTransactionUseCase;
    }

    @PostMapping("evaluateFraud")
    @Operation(
            summary = "Evaluate a transaction for fraud",
            description = "Screens the transaction against all fraud rules configured for the given merchant and returns a risk score (Low/Medium/High)."
    )
    @ApiResponse(responseCode = "200", description = "Fraud evaluation result")
    public EvaluationResponse evaluateFraud(@RequestBody TransactionRequest transactionInfo)
            throws ParseException, InvalidInfoException, EntityPreprocessingException {

        Transaction transaction = TransactionMapper.toDomain(transactionInfo);
        EvaluationResult result = evaluateTransactionUseCase.evaluate(transaction);
        return TransactionMapper.toResponse(result);
    }
}
