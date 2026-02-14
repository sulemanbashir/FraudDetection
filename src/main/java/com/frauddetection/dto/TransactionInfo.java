package com.frauddetection.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import com.frauddetection.validation.groups.TerminalThreatScoreValidator;

public record TransactionInfo(
        Double amount,
        String currency,
        @NotEmpty String terminalId,
        @NotEmpty String merchant,
        @NotBlank(groups = TerminalThreatScoreValidator.class) String terminalThreatScore,
        String city) {
}
