package com.frauddetection.service;

import java.text.ParseException;
import java.util.List;

import com.frauddetection.dto.ResponseInfo;
import com.frauddetection.enums.factory.EntityPreprocessorFactory;
import com.frauddetection.enums.factory.FraudTypeEvaluatorFactory;
import com.frauddetection.exception.InvalidInfoException;
import com.frauddetection.model.FraudParameterDetails;
import com.frauddetection.repository.FraudParameterDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frauddetection.dto.TransactionInfo;
import com.frauddetection.validation.FraudValidator;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class FraudDetectionService {

	@Autowired
	private FraudParameterDetailsRepository fraudParameterDetailsRepository;

	@Autowired
	private FraudValidator fraudValidator;

	@Autowired
	private FraudScoreEvaluatorService fraudScoreEvaluatorService;

	public ResponseInfo evaluateFraud(TransactionInfo transactionInfo) throws ParseException, InvalidInfoException {
		Double fraudScore = 0d;

		log.info("Going To fetch fraud parameters for merchant:[" + transactionInfo.getMerchant() + "]");
		List<FraudParameterDetails> fraudParameterDetailsList = fraudParameterDetailsRepository.findByMerchantName(transactionInfo.getMerchant());

		log.info("fraud parameters fetched for merchant:[" + transactionInfo.getMerchant() + "] are " + fraudParameterDetailsList);

		for (FraudParameterDetails fraudParameterDetails : fraudParameterDetailsList) {
			if (fraudValidator.validateFraudScore(fraudParameterDetails, transactionInfo)) {
				log.info("Going to Preprocess EntityId[" + fraudParameterDetails.getFraudRuleName() + "]");
				EntityPreprocessorFactory.getEntityPreprocessorEnum(fraudParameterDetails.getEntity().getId()).getEntityPreprocessor().normalizeEntity(transactionInfo, fraudParameterDetails);
				log.info("Going to Process Fraud Rule[" + fraudParameterDetails.getEntity().getId() + "]");
				ResponseInfo responseInfo = FraudTypeEvaluatorFactory.getFraudRuleEvaluatorEnum(fraudParameterDetails.getFraudParameterApi().getTypeCode().getTypeCode())
						.createFraudRuleEvaluatorObject(fraudParameterDetails.getFraudParameterApi().getId()).applyFraudRules(transactionInfo, fraudParameterDetails);

				log.info("Response received after evaluating rule " + responseInfo);
				fraudScore += Double.parseDouble(responseInfo.getFraudScore());
			}

		}
		log.info("Going to evaluate fraud score[" + fraudScore + "]");
		return fraudScoreEvaluatorService.evaluateFraudScore(fraudScore);
	}

}
