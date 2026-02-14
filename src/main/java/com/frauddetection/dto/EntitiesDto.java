package com.frauddetection.dto;

import jakarta.validation.constraints.NotNull;

import com.frauddetection.validation.groups.OperationalFraudRuleInsertValidation;

public record EntitiesDto(
        @NotNull(groups = OperationalFraudRuleInsertValidation.class)
        Long id,
        String name,
        DataTypeDto datatype
) {
}
