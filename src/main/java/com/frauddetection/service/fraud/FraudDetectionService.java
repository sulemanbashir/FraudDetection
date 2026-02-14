package com.frauddetection.service.fraud;

import java.text.ParseException;
import java.util.List;

import com.frauddetection.dto.ResponseInfo;
import com.frauddetection.exception.EntityPreprocessingException;
import com.frauddetection.exception.InvalidInfoException;
import com.frauddetection.model.FraudParameterDetails;
import com.frauddetection.repository.FraudParameterDetailsRepository;
import com.frauddetection.service.preprocessing.EntityPreprocessor;
import com.frauddetection.service.preprocessing.EntityPreprocessorService;
import org.springframework.stereotype.Service;

import com.frauddetection.dto.TransactionInfo;
import com.frauddetection.validation.FraudValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class FraudDetectionService {
    private static final Logger log = LogManager.getLogger(FraudDetectionService.class);

    private final FraudParameterDetailsRepository fraudParameterDetailsRepository;
    private final FraudValidator fraudValidator;
    private final FraudScoreEvaluatorService fraudScoreEvaluatorService;
    private final EntityPreprocessorService entityPreprocessorService;
    private final FraudRuleEvaluatorService fraudRuleEvaluatorService;

    public FraudDetectionService(
            FraudParameterDetailsRepository fraudParameterDetailsRepository,
            FraudValidator fraudValidator,
            FraudScoreEvaluatorService fraudScoreEvaluatorService,
            EntityPreprocessorService entityPreprocessorService,
            FraudRuleEvaluatorService fraudRuleEvaluatorService) {
        this.fraudParameterDetailsRepository = fraudParameterDetailsRepository;
        this.fraudValidator = fraudValidator;
        this.fraudScoreEvaluatorService = fraudScoreEvaluatorService;
        this.entityPreprocessorService = entityPreprocessorService;
        this.fraudRuleEvaluatorService = fraudRuleEvaluatorService;
    }

    public ResponseInfo evaluateFraud(TransactionInfo transactionInfo) throws ParseException, InvalidInfoException, EntityPreprocessingException {
        Double fraudScore = 0d;

        log.info("Going To fetch fraud parameters for merchant:[" + transactionInfo.merchant() + "]");
        List<FraudParameterDetails> fraudParameterDetailsList = fraudParameterDetailsRepository.findByMerchantName(transactionInfo.merchant());

        log.info("fraud parameters fetched for merchant:[" + transactionInfo.merchant() + "] are " + fraudParameterDetailsList);

        for (FraudParameterDetails fraudParameterDetails : fraudParameterDetailsList) {
            if (fraudValidator.validateFraudScore(fraudParameterDetails, transactionInfo)) {
                log.info("Going to Preprocess EntityId[" + fraudParameterDetails.getFraudRuleName() + "]");

                EntityPreprocessor preprocessor = entityPreprocessorService.getPreprocessorByDatabaseValue(
                        fraudParameterDetails.getEntity().getName());
                preprocessor.normalizeEntity(transactionInfo, fraudParameterDetails);

                log.info("Going to Process Fraud Rule[" + fraudParameterDetails.getEntity().getId() + "]");

                FraudRuleEvaluator evaluator = fraudRuleEvaluatorService.getEvaluatorByFraudTypeCode(
                        fraudParameterDetails.getFraudParameterApi().getTypeCode().getTypeCode());
                ResponseInfo responseInfo = evaluator.applyFraudRules(transactionInfo, fraudParameterDetails);

                log.info("Response received after evaluating rule " + responseInfo);
                fraudScore += Double.parseDouble(responseInfo.fraudScore());
            }

        }
        log.info("Going to evaluate fraud score[" + fraudScore + "]");
        return fraudScoreEvaluatorService.evaluateFraudScore(fraudScore);
    }

}
