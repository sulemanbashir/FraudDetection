package com.frauddetection.evaluation.port.in;

import com.frauddetection.evaluation.domain.model.Transaction;
import com.frauddetection.evaluation.domain.valueobject.EvaluationResult;
import com.frauddetection.shared.exception.EntityPreprocessingException;
import com.frauddetection.shared.exception.InvalidInfoException;

import java.text.ParseException;

public interface EvaluateTransactionUseCase {

    EvaluationResult evaluate(Transaction transaction) throws ParseException, InvalidInfoException, EntityPreprocessingException;
}
