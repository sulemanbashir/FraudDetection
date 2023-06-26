package com.frauddetection.service.entitypreprocessing;

import java.lang.reflect.Field;

import org.springframework.stereotype.Component;

import com.frauddetection.dto.TransactionInfo;
import com.frauddetection.model.FraudParameterDetails;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class DefaultPreprocessor implements EntityPreprocessor {

	@Override
	public void normalizeEntity(TransactionInfo transactionInfo, FraudParameterDetails fraudParameterDetails) {
		try {
			log.info("Going to set entity[" + fraudParameterDetails.getEntity().getName() + "] value in actual criteria value from transaction");
			fraudParameterDetails.setActualCriteriaValue(getFieldValue(transactionInfo, fraudParameterDetails.getEntity().getName()));
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
			log.error("Error found: {}", e);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			log.error("Error found: {}", e);
		}

	}

	private String getFieldValue(TransactionInfo transactionInfo, String fieldName) throws NoSuchFieldException, IllegalAccessException {
		Field field = transactionInfo.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		return (String) field.get(transactionInfo);
	}
}
