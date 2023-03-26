package com.frauddetection.rules.operational.factory.enums;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Optional;

import javax.annotation.PostConstruct;

import com.frauddetection.rules.operational.service.operators.Operator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public enum OperatorFactory {

	OPERATOR_CODE_GREATER_THAN(3) {
		@Override
		public Operator createOperatorOpject() {
			log.info("Returing Operator: GREATER_THAN");
			return operator;
		}
	},OPERATOR_CODE_EQUAL(1) {
		@Override
		public Operator createOperatorOpject() {
			log.info("Returing Operator: EQUAL");
			return operator;
		}
	},OPERATOR_CODE_IN(5) {
		@Override
		public Operator createOperatorOpject() {
			log.info("Returing Operator: IN");
			return operator;
		}
	},OPERATOR_CODE_NOT_IN(6) {
		@Override
		public Operator createOperatorOpject() {
			log.info("Returing Operator: NOT_IN");
			return operator;
		}
	};

	public Operator operator;

	private long operatorId;

	OperatorFactory(long i) {
		operatorId = i;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	public long getOperatorId() {
		return operatorId;
	}

	public abstract Operator createOperatorOpject();

	public static OperatorFactory getOperatorEnum(long operatorId) {
		Optional<OperatorFactory> car = Arrays.stream(OperatorFactory.values())
				.filter(c -> c.getOperatorId() == operatorId).findFirst();
		return car.get();
	}

	@Component
	public static class setOperator{

		@Autowired
		private Operator greaterThanOperator;
		
		@Autowired
		private Operator equalOperator;
		
		@Autowired
		private Operator inOperator;
		
		@Autowired
		private Operator notInOperator;

		@PostConstruct
		public void setOperatorService() {
			log.info("Initialzing Operator Factory Objects By Spring Autowired!");
			for (OperatorFactory op : EnumSet.allOf(OperatorFactory.class)) {
				if (OperatorFactory.OPERATOR_CODE_GREATER_THAN.equals(op)) {
					log.info("Operator: GREATER_THAN initialized successfully");
					op.setOperator(greaterThanOperator);
				}else if (OperatorFactory.OPERATOR_CODE_EQUAL.equals(op)) {
					log.info("Operator: EQAUL initialized successfully");
					op.setOperator(equalOperator);
				}else if (OperatorFactory.OPERATOR_CODE_IN.equals(op)) {
					log.info("Operator: IN initialized successfully");
					op.setOperator(inOperator);
				}else if (OperatorFactory.OPERATOR_CODE_NOT_IN.equals(op)) {
					log.info("Operator: NOT_IN initialized successfully");
					op.setOperator(notInOperator);
				}
			}
		}
	}
}
