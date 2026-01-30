package com.frauddetection.service.fraud;

import com.frauddetection.enums.FraudTypeCode;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Log4j2
public class FraudRuleEvaluatorService {

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
