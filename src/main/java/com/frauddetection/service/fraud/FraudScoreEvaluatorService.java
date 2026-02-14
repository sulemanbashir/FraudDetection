package com.frauddetection.service.fraud;

import java.util.List;

import com.frauddetection.dto.ResponseInfo;
import com.frauddetection.model.FraudScoreLevel;
import com.frauddetection.repository.FraudScoreLevelRepository;
import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class FraudScoreEvaluatorService {
    private static final Logger log = LogManager.getLogger(FraudScoreEvaluatorService.class);

    private final FraudScoreLevelRepository fraudScoreLevelRepository;
    private List<FraudScoreLevel> cachedScoreLevels;

    public FraudScoreEvaluatorService(FraudScoreLevelRepository fraudScoreLevelRepository) {
        this.fraudScoreLevelRepository = fraudScoreLevelRepository;
    }

    @PostConstruct
    void loadScoreLevels() {
        cachedScoreLevels = fraudScoreLevelRepository.findAll();
        log.info("Loaded {} fraud score levels", cachedScoreLevels.size());
    }

    public ResponseInfo evaluateFraudScore(Double fraudScore) {
        if (fraudScore > 100) {
            log.info("Fraud Score max limit exceeded. Capping at HIGH.");
            return new ResponseInfo("H", "High", "100", null);
        }

        for (FraudScoreLevel fraudScoreLevel : cachedScoreLevels) {
            if (fraudScore >= fraudScoreLevel.getMinimunRange() && fraudScore <= fraudScoreLevel.getMaximumRange()) {
                log.info("Fraud Evaluated. Score[{}], Status[{}], Message[{}]",
                        fraudScore, fraudScoreLevel.getScoreLevel(), fraudScoreLevel.getName());
                return new ResponseInfo(fraudScoreLevel.getScoreLevel(), fraudScoreLevel.getName(), fraudScore.toString(), null);
            }
        }
        return ResponseInfo.empty();
    }
}
