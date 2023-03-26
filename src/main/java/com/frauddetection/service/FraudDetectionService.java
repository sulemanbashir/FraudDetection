package com.frauddetection.service;

import java.text.ParseException;
import java.util.List;

import com.frauddetection.repository.FraudParameterDetailsRepository;
import com.frauddetection.validation.FraudValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frauddetection.dto.ResponseInfo;
import com.frauddetection.dto.TransactionInfo;
import com.frauddetection.factory.enums.EntityPreprocessorFactory;
import com.frauddetection.factory.enums.FraudTypeEvaluatorFactory;
import com.frauddetection.model.FraudParameterDetails;
import com.frauddetection.rules.operational.exception.InvalidInfoException;

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
		ResponseInfo responseInfo = new ResponseInfo();
		Double fraudScore = 0d;

		log.info("Going To fetch fraud parameters for merchand:[" + transactionInfo.getMerchand() +"]");
		List<FraudParameterDetails> fraudParameterDetailsList = fraudParameterDetailsRepository
				.findByMerchantName(transactionInfo.getMerchand());

		log.info("fraud parameters fetched for merchand:[" + transactionInfo.getMerchand() +"] are "+fraudParameterDetailsList);
		for (FraudParameterDetails fraudParameterDetails : fraudParameterDetailsList) {
			if (fraudValidator.validateFraudScore(fraudParameterDetails, transactionInfo)) {
				log.info("Going to Preprocess EntityId[" + fraudParameterDetails.getEntity().getId() +"]");
				EntityPreprocessorFactory.getEntityPreprocessorEnum(fraudParameterDetails.getEntity().getId())
						.getEntityPreprocessor().normalizeEntity(transactionInfo, fraudParameterDetails);

				responseInfo = FraudTypeEvaluatorFactory
						.getFraudRuleEvaluatorEnum(
								fraudParameterDetails.getfraudParameterApi().getTypeCode().getTypeCode())
						.createFraudRuleEvaluatorOpject(fraudParameterDetails.getfraudParameterApi().getId())
						.applyFraudRules(transactionInfo, fraudParameterDetails);

				log.info("Reponse received after evaluating rule "+ responseInfo );
				fraudScore += Double.parseDouble(responseInfo.getFraudScore());
			}

		}
		log.info("Going to evaluate fraud score[" + fraudScore +"]");
		return fraudScoreEvaluatorService.evaluateFraudScore(fraudScore);
	}

}
