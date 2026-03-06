package com.frauddetection.evaluation.adapter.in.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.frauddetection.shared.exception.ViolationErrorResponse;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Fraud evaluation result")
public record EvaluationResponse(
        @Schema(description = "Evaluation status", example = "High")
        String status,

        @Schema(description = "Additional message")
        String message,

        @Schema(description = "Calculated fraud score", example = "90.0")
        String fraudScore,

        ViolationErrorResponse violationErrorResponse) {

    public static EvaluationResponse empty() {
        return new EvaluationResponse(null, null, "0", null);
    }
}
