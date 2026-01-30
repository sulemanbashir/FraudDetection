package com.frauddetection.config;

import com.frauddetection.enums.EntityType;
import com.frauddetection.service.preprocessing.EntityPreprocessor;
import com.frauddetection.service.preprocessing.AmountPreprocessor;
import com.frauddetection.service.preprocessing.DefaultPreprocessor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Log4j2
public class EntityPreprocessorRegistry {
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
