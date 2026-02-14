package com.frauddetection.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MapperConfig {
    private static final Logger log = LogManager.getLogger(MapperConfig.class);

    @Bean
    public ModelMapper modelMapper() {
        log.info("Initializing ModelMapper bean with default configuration");

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STANDARD)
                .setSkipNullEnabled(true)  // Skip null values during mapping
                .setAmbiguityIgnored(true); // Ignore ambiguous mappings

        log.info("ModelMapper bean initialized successfully");

        return modelMapper;
    }
}
