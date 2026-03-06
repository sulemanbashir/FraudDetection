package com.frauddetection.evaluation.adapter.in.rest;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

@Schema(description = "Transaction data to evaluate against fraud rules")
public record TransactionRequest(
        @Schema(description = "Transaction amount", example = "600.0")
        Double amount,

        @Schema(description = "Currency code", example = "EUR")
        String currency,

        @Schema(description = "Terminal identifier", example = "123")
        @NotEmpty String terminalId,

        @Schema(description = "Merchant name", example = "KFC")
        @NotEmpty String merchant,

        @Schema(description = "Terminal threat score", example = "80")
        @NotBlank(groups = TerminalThreatScoreValidator.class) String terminalThreatScore,

        @Schema(description = "City of the transaction", example = "Lahore")
        String city) {
}
