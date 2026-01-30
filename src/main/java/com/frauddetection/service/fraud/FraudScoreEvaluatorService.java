package com.frauddetection.service.fraud;

import java.util.List;

import com.frauddetection.dto.ResponseInfo;
import com.frauddetection.model.FraudScoreLevel;
import com.frauddetection.repository.FraudScoreLevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class FraudScoreEvaluatorService {

    @Autowired
    private FraudScoreLevelRepository fraudScoreLevelRepository;

    public ResponseInfo evaluateFraudScore(Double fraudScore) {
        ResponseInfo responseInfo = new ResponseInfo();

        if (fraudScore > 100) {
            log.info("Fraud Score max limit exceeded!! Returning Transaction with HIGH Fraud thread.");
            FraudScoreLevel highFraud = fraudScoreLevelRepository.findByScoreLevel("H");
            responseInfo.setFraudScore("100");
            responseInfo.setStatus(highFraud.getScoreLevel());
            responseInfo.setMessage(highFraud.getName());
            log.info("Fraud Evaluated!!. Fraud Score[ " + fraudScore + "], Status[" + highFraud.getScoreLevel() + "], Message[" + highFraud.getName() + "]");
            return responseInfo;
        }

        log.info("Going to fetch Fraud Score from db");
        List<FraudScoreLevel> FraudScoreLevelList = fraudScoreLevelRepository.findAll();
        log.info("Fraud Score fetched " + FraudScoreLevelList);
        for (FraudScoreLevel fraudScoreLevel : FraudScoreLevelList) {
            if (fraudScore >= fraudScoreLevel.getMinimunRange() && fraudScore <= fraudScoreLevel.getMaximumRange()) {
                log.info("Fraud Evaluated!!. Fraud Score[ " + fraudScore + "], Status[" + fraudScoreLevel.getScoreLevel() + "], Message[" + fraudScoreLevel.getName() + "]");
                responseInfo.setFraudScore(fraudScore.toString());
                responseInfo.setStatus(fraudScoreLevel.getScoreLevel());
                responseInfo.setMessage(fraudScoreLevel.getName());
                break;
            }
        }
        return responseInfo;
    }
}
