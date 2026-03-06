package com.frauddetection.evaluation.port.out;

import com.frauddetection.evaluation.domain.model.ScoreLevel;

import java.util.List;

public interface ScoreLevelProvider {

    List<ScoreLevel> getScoreLevels();
}
