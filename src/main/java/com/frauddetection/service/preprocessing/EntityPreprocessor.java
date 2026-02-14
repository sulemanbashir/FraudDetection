package com.frauddetection.service.preprocessing;

import com.frauddetection.dto.TransactionInfo;
import com.frauddetection.exception.EntityPreprocessingException;
import com.frauddetection.model.FraudParameterDetails;

public interface EntityPreprocessor {

    void normalizeEntity(TransactionInfo transactionInfo, FraudParameterDetails fraudParameterDetails)
            throws EntityPreprocessingException;
}
