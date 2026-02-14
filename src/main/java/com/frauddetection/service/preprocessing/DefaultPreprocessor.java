package com.frauddetection.service.preprocessing;

import java.util.Map;
import java.util.function.Function;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.frauddetection.dto.TransactionInfo;
import com.frauddetection.exception.EntityPreprocessingException;
import com.frauddetection.model.FraudParameterDetails;

@Component
public class DefaultPreprocessor implements EntityPreprocessor {
    private static final Logger log = LogManager.getLogger(DefaultPreprocessor.class);

    private static final Map<String, Function<TransactionInfo, String>> FIELD_EXTRACTORS = Map.of(
            "terminalId", TransactionInfo::terminalId,
            "city", TransactionInfo::city,
            "currency", TransactionInfo::currency,
            "merchant", TransactionInfo::merchant,
            "terminalThreatScore", TransactionInfo::terminalThreatScore
    );

    @Override
    public void normalizeEntity(TransactionInfo transactionInfo, FraudParameterDetails fraudParameterDetails)
            throws EntityPreprocessingException {

        if (transactionInfo == null || fraudParameterDetails == null) {
            throw new EntityPreprocessingException("TransactionInfo and FraudParameterDetails cannot be null");
        }

        if (fraudParameterDetails.getEntity() == null || fraudParameterDetails.getEntity().getName() == null) {
            throw new EntityPreprocessingException("Entity name cannot be null");
        }

        String entityName = fraudParameterDetails.getEntity().getName();
        log.info("Going to set entity[{}] value in actual criteria value from transaction", entityName);

        Function<TransactionInfo, String> extractor = FIELD_EXTRACTORS.get(entityName);
        if (extractor == null) {
            throw new EntityPreprocessingException(
                    String.format("No field extractor registered for entity '%s'", entityName));
        }

        String fieldValue = extractor.apply(transactionInfo);
        if (fieldValue == null) {
            log.warn("Field value is null for entity[{}], setting empty string", entityName);
            fieldValue = "";
        }

        fraudParameterDetails.setActualCriteriaValue(fieldValue);
        log.info("Successfully set actualCriteriaValue: {}", fieldValue);
    }
}
