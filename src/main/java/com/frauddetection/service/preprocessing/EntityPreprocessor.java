package com.frauddetection.service.preprocessing;

import org.springframework.stereotype.Component;

import com.frauddetection.dto.TransactionInfo;
import com.frauddetection.exception.EntityPreprocessingException;
import com.frauddetection.model.FraudParameterDetails;

@Component
public interface EntityPreprocessor {

    void normalizeEntity(TransactionInfo transactionInfo, FraudParameterDetails fraudParameterDetails)
            throws EntityPreprocessingException;
}
