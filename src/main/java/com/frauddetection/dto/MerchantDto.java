package com.frauddetection.dto;

import jakarta.validation.constraints.NotBlank;

import com.frauddetection.validation.groups.OperationalFraudRuleInsertValidation;
import com.frauddetection.validation.groups.OperationalFraudRuleUpdateValidation;

public record MerchantDto(
        Long id,
        @NotBlank(groups = {OperationalFraudRuleInsertValidation.class, OperationalFraudRuleUpdateValidation.class})
        String name
) {
}
