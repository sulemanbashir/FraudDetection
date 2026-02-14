package com.frauddetection.service.preprocessing;

import com.frauddetection.enums.EntityType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class EntityPreprocessorService {
    private static final Logger log = LogManager.getLogger(EntityPreprocessorService.class);

    private final Map<EntityType, EntityPreprocessor> preprocessorRegistry;
    private final EntityPreprocessor defaultPreprocessor;

    @Autowired
    public EntityPreprocessorService(
            @Qualifier("entityPreprocessorMap") Map<EntityType, EntityPreprocessor> preprocessorRegistry,
            @Qualifier("defaultEntityPreprocessor") EntityPreprocessor defaultPreprocessor) {
        this.preprocessorRegistry = preprocessorRegistry;
        this.defaultPreprocessor = defaultPreprocessor;
        log.info("EntityPreprocessorService initialized with {} registered preprocessors", preprocessorRegistry.size());
    }

    public EntityPreprocessor getPreprocessor(EntityType entityType) {
        EntityPreprocessor preprocessor = preprocessorRegistry.get(entityType);

        if (preprocessor == null) {
            log.debug("No specific preprocessor found for {}, using default", entityType);
            return defaultPreprocessor;
        }

        log.debug("Retrieved preprocessor for {}: {}", entityType, preprocessor.getClass().getSimpleName());
        return preprocessor;
    }


    public EntityPreprocessor getPreprocessorByDatabaseValue(String entityName) {
        try {
            // Convert database string to ENUM
            EntityType entityType = EntityType.fromDatabaseValue(entityName);
            return getPreprocessor(entityType);
        } catch (IllegalArgumentException e) {
            // Unknown entity type - use default preprocessor
            log.debug("Unknown entity type '{}', using default preprocessor", entityName);
            return defaultPreprocessor;
        }
    }
}
