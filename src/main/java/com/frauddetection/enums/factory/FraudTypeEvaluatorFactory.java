package com.frauddetection.enums.factory;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.frauddetection.service.FraudRuleEvaluator;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public enum FraudTypeEvaluatorFactory {

	OPERATIONAL_TYPE_FRAUD("O") {
		@Override
		public FraudRuleEvaluator createFraudRuleEvaluatorObject(long apiId) {
			log.info("Returing FraudRuleEvaluator: OPERATIONAL_TYPE_FRAUD");
			return FraudRuleEvaluatorApiFactory.getFraudRuleEvaluatorEnum(apiId).getFraudRuleEvaluator();
		}
	};

	private String fraudType;

	FraudTypeEvaluatorFactory(String i) {
		fraudType = i;
	}

	public String getFraudType() {
		return fraudType;
	}

	public abstract FraudRuleEvaluator createFraudRuleEvaluatorObject(long apiId);

	public static FraudTypeEvaluatorFactory getFraudRuleEvaluatorEnum(String fraudType) {
		Optional<FraudTypeEvaluatorFactory> car = Arrays.stream(FraudTypeEvaluatorFactory.values()).filter(c -> c.getFraudType().equalsIgnoreCase(fraudType)).findFirst();
		return car.get();
	}

}
