package com.frauddetection.service.fraud;

import com.frauddetection.enums.FraudTypeCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class FraudRuleEvaluatorService {
    private static final Logger log = LogManager.getLogger(FraudRuleEvaluatorService.class);

    private final Map<FraudTypeCode, FraudRuleEvaluator> fraudTypeRegistry;

    public FraudRuleEvaluatorService(
            @Qualifier("fraudTypeEvaluatorRegistry") Map<FraudTypeCode, FraudRuleEvaluator> fraudTypeRegistry) {
        this.fraudTypeRegistry = fraudTypeRegistry;
        log.info("FraudRuleEvaluatorService initialized with {} fraud type evaluators", fraudTypeRegistry.size());
    }

    public FraudRuleEvaluator getEvaluatorByFraudType(FraudTypeCode fraudTypeCode) {
        FraudRuleEvaluator evaluator = fraudTypeRegistry.get(fraudTypeCode);

        if (evaluator == null) {
            log.error("No fraud rule evaluator found for fraudTypeCode={}", fraudTypeCode);
            throw new IllegalArgumentException(
                    String.format("No fraud rule evaluator registered for fraudTypeCode=%s", fraudTypeCode)
            );
        }

        log.debug("Retrieved fraud rule evaluator for fraudTypeCode={}: {}",
                fraudTypeCode, evaluator.getClass().getSimpleName());
        return evaluator;
    }

    public FraudRuleEvaluator getEvaluatorByFraudTypeCode(String fraudTypeCodeStr) {
        try {
            // Convert database string to ENUM
            FraudTypeCode fraudTypeCode = FraudTypeCode.fromCode(fraudTypeCodeStr);
            return getEvaluatorByFraudType(fraudTypeCode);
        } catch (IllegalArgumentException e) {
            log.error("Invalid fraud type code: {}", fraudTypeCodeStr);
            throw new IllegalArgumentException(
                    String.format("Invalid fraud type code: %s", fraudTypeCodeStr), e
            );
        }
    }

    public boolean hasEvaluatorForFraudType(FraudTypeCode fraudTypeCode) {
        return fraudTypeRegistry.containsKey(fraudTypeCode);
    }
}
