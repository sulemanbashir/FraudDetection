package com.frauddetection.evaluation.domain.service.preprocessing;

import java.util.Map;
import java.util.function.Function;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.frauddetection.evaluation.domain.model.FraudRule;
import com.frauddetection.evaluation.domain.model.Transaction;
import com.frauddetection.shared.exception.EntityPreprocessingException;

@Component
public class DefaultPreprocessor implements EntityPreprocessor {
    private static final Logger log = LogManager.getLogger(DefaultPreprocessor.class);

    private static final Map<String, Function<Transaction, String>> FIELD_EXTRACTORS = Map.of(
            "terminalId", Transaction::terminalId,
            "city", Transaction::city,
            "currency", txn -> txn.money() != null ? txn.money().currency() : null,
            "merchant", Transaction::merchant,
            "terminalThreatScore", Transaction::terminalThreatScore
    );

    @Override
    public String extractValue(Transaction transaction, FraudRule rule)
            throws EntityPreprocessingException {

        if (transaction == null || rule == null) {
            throw new EntityPreprocessingException("Transaction and FraudRule cannot be null");
        }

        if (rule.entityType() == null) {
            throw new EntityPreprocessingException("Entity type cannot be null");
        }

        String entityName = rule.entityType().getValue();
        log.info("Extracting entity[{}] value from transaction", entityName);

        Function<Transaction, String> extractor = FIELD_EXTRACTORS.get(entityName);
        if (extractor == null) {
            throw new EntityPreprocessingException(
                    String.format("No field extractor registered for entity '%s'", entityName));
        }

        String fieldValue = extractor.apply(transaction);
        if (fieldValue == null) {
            log.warn("Field value is null for entity[{}], returning empty string", entityName);
            fieldValue = "";
        }

        log.info("Extracted value: {}", fieldValue);
        return fieldValue;
    }
}
