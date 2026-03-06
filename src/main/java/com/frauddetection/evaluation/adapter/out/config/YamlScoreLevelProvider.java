package com.frauddetection.evaluation.adapter.out.config;

import java.util.List;

import com.frauddetection.evaluation.domain.model.ScoreLevel;
import com.frauddetection.evaluation.port.out.ScoreLevelProvider;
import org.springframework.stereotype.Component;

@Component
public class YamlScoreLevelProvider implements ScoreLevelProvider {

    private final FraudDetectionProperties properties;

    public YamlScoreLevelProvider(FraudDetectionProperties properties) {
        this.properties = properties;
    }

    @Override
    public List<ScoreLevel> getScoreLevels() {
        return properties.getScoreLevels().stream()
                .map(sl -> new ScoreLevel(sl.code(), sl.name(), sl.minRange(), sl.maxRange()))
                .toList();
    }
}
