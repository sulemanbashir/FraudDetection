package com.frauddetection.shared.registry;

import com.frauddetection.shared.enums.EntityType;
import com.frauddetection.evaluation.domain.service.preprocessing.EntityPreprocessor;
import com.frauddetection.evaluation.domain.service.preprocessing.AmountPreprocessor;
import com.frauddetection.evaluation.domain.service.preprocessing.DefaultPreprocessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class EntityPreprocessorRegistry {
    private static final Logger log = LogManager.getLogger(EntityPreprocessorRegistry.class);
    @Bean(name = "entityPreprocessorMap")
    public Map<EntityType, EntityPreprocessor> entityPreprocessorMap(
            AmountPreprocessor amountPreprocessor,
            DefaultPreprocessor defaultPreprocessor) {

        log.info("Initializing EntityPreprocessor Registry with ENUM-based keys");

        Map<EntityType, EntityPreprocessor> registry = new HashMap<>();

        registry.put(EntityType.AMOUNT, amountPreprocessor);
        log.info("Registered: {} → AmountPreprocessor", EntityType.AMOUNT);

        log.info("EntityPreprocessor Registry initialized successfully with {} preprocessors", registry.size());

        return registry;
    }

    @Bean
    public EntityPreprocessor defaultEntityPreprocessor(DefaultPreprocessor defaultPreprocessor) {
        return defaultPreprocessor;
    }
}
