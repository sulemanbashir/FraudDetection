package com.frauddetection.evaluation.domain.service.preprocessing;

import com.frauddetection.evaluation.domain.model.FraudRule;
import com.frauddetection.evaluation.domain.model.Transaction;
import com.frauddetection.shared.exception.EntityPreprocessingException;

public interface EntityPreprocessor {

    String extractValue(Transaction transaction, FraudRule rule)
            throws EntityPreprocessingException;
}
