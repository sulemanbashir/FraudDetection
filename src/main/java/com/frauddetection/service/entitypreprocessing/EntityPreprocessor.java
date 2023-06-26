package com.frauddetection.service.entitypreprocessing;

import org.springframework.stereotype.Component;

import com.frauddetection.dto.TransactionInfo;
import com.frauddetection.model.FraudParameterDetails;

@Component
public interface EntityPreprocessor {

	void normalizeEntity(TransactionInfo transactionInfo, FraudParameterDetails fraudParameterDetails);
}
