package com.frauddetection.service.preprocessing;

import java.lang.reflect.Field;

import org.springframework.stereotype.Component;

import com.frauddetection.dto.TransactionInfo;
import com.frauddetection.exception.EntityPreprocessingException;
import com.frauddetection.model.FraudParameterDetails;

import lombok.extern.log4j.Log4j2;

/**
 * Default entity preprocessor for all entities except AMOUNT.
 * Uses reflection to extract field values from TransactionInfo.
 */
@Component
@Log4j2
public class DefaultPreprocessor implements EntityPreprocessor {

    @Override
    public void normalizeEntity(TransactionInfo transactionInfo, FraudParameterDetails fraudParameterDetails)
            throws EntityPreprocessingException {

        if (transactionInfo == null || fraudParameterDetails == null) {
            throw new EntityPreprocessingException("TransactionInfo and FraudParameterDetails cannot be null");
        }

        if (fraudParameterDetails.getEntity() == null || fraudParameterDetails.getEntity().getName() == null) {
            throw new EntityPreprocessingException("Entity name cannot be null");
        }

        try {
            String entityName = fraudParameterDetails.getEntity().getName();
            log.info("Going to set entity[" + entityName + "] value in actual criteria value from transaction");

            String fieldValue = getFieldValue(transactionInfo, entityName);

            if (fieldValue == null) {
                log.warn("Field value is null for entity[{}], setting empty string", entityName);
                fieldValue = "";
            }

            // IMPORTANT: Always set actualCriteriaValue (LSP compliance)
            fraudParameterDetails.setActualCriteriaValue(fieldValue);
            log.info("Successfully set actualCriteriaValue: {}", fieldValue);

        } catch (NoSuchFieldException e) {
            String errorMsg = String.format("Field '%s' not found in TransactionInfo class",
                    fraudParameterDetails.getEntity().getName());
            log.error(errorMsg, e);
            throw new EntityPreprocessingException(errorMsg, e);

        } catch (IllegalAccessException e) {
            String errorMsg = String.format("Cannot access field '%s' in TransactionInfo class",
                    fraudParameterDetails.getEntity().getName());
            log.error(errorMsg, e);
            throw new EntityPreprocessingException(errorMsg, e);

        } catch (ClassCastException e) {
            String errorMsg = String.format("Field '%s' is not a String type",
                    fraudParameterDetails.getEntity().getName());
            log.error(errorMsg, e);
            throw new EntityPreprocessingException(errorMsg, e);
        }
    }

    private String getFieldValue(TransactionInfo transactionInfo, String fieldName)
            throws NoSuchFieldException, IllegalAccessException {
        Field field = transactionInfo.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        Object value = field.get(transactionInfo);

        // Handle null values gracefully
        if (value == null) {
            return null;
        }

        // Convert to String - this will throw ClassCastException if not a String
        // For non-String fields, consider using value.toString() instead
        return value.toString();
    }
}
