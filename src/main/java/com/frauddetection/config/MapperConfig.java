package com.frauddetection.config;

import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@Log4j2
public class MapperConfig {

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
