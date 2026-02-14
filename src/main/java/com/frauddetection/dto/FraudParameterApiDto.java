package com.frauddetection.dto;

import jakarta.validation.constraints.NotNull;

import com.frauddetection.validation.groups.OperationalFraudRuleInsertValidation;

public record FraudParameterApiDto(
        @NotNull(groups = OperationalFraudRuleInsertValidation.class)
        Long id,
        String name,
        String description,
        FraudTypeDto typeCode,
        String classDetail
) {
}
