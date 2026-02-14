package com.frauddetection.config;

import com.frauddetection.enums.FraudTypeCode;
import com.frauddetection.service.fraud.FraudRuleEvaluator;
import com.frauddetection.service.fraud.operational.OperationalFraudEvaluatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class FraudRuleEvaluatorRegistry {
    private static final Logger log = LogManager.getLogger(FraudRuleEvaluatorRegistry.class);
    @Bean
    public Map<FraudTypeCode, FraudRuleEvaluator> fraudTypeEvaluatorRegistry(
            OperationalFraudEvaluatorImpl operationalFraudEvaluator) {

        log.info("Initializing FraudRuleEvaluator Registry with ENUM-based keys");

        Map<FraudTypeCode, FraudRuleEvaluator> registry = new HashMap<>();

        registry.put(FraudTypeCode.OPERATIONAL, operationalFraudEvaluator);
        log.info("Registered: {} → OperationalFraudEvaluator", FraudTypeCode.OPERATIONAL);

        log.info("FraudRuleEvaluator Registry initialized successfully with {} evaluators", registry.size());

        return registry;
    }
}
