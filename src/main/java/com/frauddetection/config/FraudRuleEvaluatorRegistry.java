package com.frauddetection.config;

import com.frauddetection.enums.FraudTypeCode;
import com.frauddetection.service.fraud.FraudRuleEvaluator;
import com.frauddetection.service.fraud.operational.OperationalFraudEvaluatorImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Log4j2
public class FraudRuleEvaluatorRegistry {
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
