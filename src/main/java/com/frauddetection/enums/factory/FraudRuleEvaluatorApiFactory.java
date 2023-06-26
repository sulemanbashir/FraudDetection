package com.frauddetection.enums.factory;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.frauddetection.service.FraudRuleEvaluator;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public enum FraudRuleEvaluatorApiFactory {

	OPERATIONAL_FRAUD_EVALUATOR_IMPL(1) {
		@Override
		public FraudRuleEvaluator getFraudRuleEvaluator() {
			log.info("Returing FraudRuleEvaluatorApi: OPERATIONAL_FRAUD_EVALUATOR_IMPL");
			return fraudRuleEvaluator;
		}
	};

	private long apiId;

	public FraudRuleEvaluator fraudRuleEvaluator;

	public void setFraudRuleEvaluator(FraudRuleEvaluator fraudRuleEvaluator) {
		this.fraudRuleEvaluator = fraudRuleEvaluator;
	}

	FraudRuleEvaluatorApiFactory(long i) {
		apiId = i;
	}

	public long getApiId() {
		return apiId;
	}

	public abstract FraudRuleEvaluator getFraudRuleEvaluator();

	public static FraudRuleEvaluatorApiFactory getFraudRuleEvaluatorEnum(long apiId) {
		Optional<FraudRuleEvaluatorApiFactory> car = Arrays.stream(FraudRuleEvaluatorApiFactory.values()).filter(c -> c.getApiId() == apiId).findFirst();
		return car.get();
	}

	@Component
	public static class setFraudRuleEvaluatorService {

		@Autowired
		private FraudRuleEvaluator operationalFraudEvaluatorImpl;

		@PostConstruct
		public void setFraudRuleEvaluator() {
			log.info("Initialzing FraudRuleEvaluator Factory Objects By Spring Autowired!");
			for (FraudRuleEvaluatorApiFactory api : EnumSet.allOf(FraudRuleEvaluatorApiFactory.class)) {
				if (FraudRuleEvaluatorApiFactory.OPERATIONAL_FRAUD_EVALUATOR_IMPL.equals(api)) {
					log.info("FraudRuleEvaluator: OPERATIONAL_FRAUD_EVALUATOR_IMPL initialized successfully");
					api.setFraudRuleEvaluator(operationalFraudEvaluatorImpl);
				}

			}
		}
	}

}
