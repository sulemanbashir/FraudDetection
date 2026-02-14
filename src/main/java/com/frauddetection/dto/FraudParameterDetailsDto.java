package com.frauddetection.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.frauddetection.validation.groups.OperationalFraudRuleInsertValidation;
import com.frauddetection.validation.groups.OperationalFraudRuleUpdateValidation;

public record FraudParameterDetailsDto(
        Long id,

        @Valid
        @NotNull(groups = {OperationalFraudRuleInsertValidation.class, OperationalFraudRuleUpdateValidation.class})
        MerchantDto merchant,

        @Valid
        @NotNull(groups = {OperationalFraudRuleInsertValidation.class})
        FraudParameterApiDto fraudParameterApi,

        @Valid
        @NotNull(groups = {OperationalFraudRuleInsertValidation.class})
        EntitiesDto entity,

        @Valid
        @NotNull(groups = {OperationalFraudRuleInsertValidation.class})
        OperatorDto operator,

        UnitDto unit,

        @NotBlank(groups = {OperationalFraudRuleInsertValidation.class, OperationalFraudRuleUpdateValidation.class})
        String fraudRuleName,

        @NotNull(groups = OperationalFraudRuleInsertValidation.class)
        Double fraudScore,

        Integer terminalThreatThreshold,

        @NotBlank(groups = OperationalFraudRuleInsertValidation.class)
        String fraudCriteriaValue1,

        String fraudCriteriaValue2,

        String sourceEntityTable,

        String sourceEntityColumn,

        Integer timeWindow,

        String exchangeCode,

        String toCurrCode,

        String actualCriteriaValue
) {
}
