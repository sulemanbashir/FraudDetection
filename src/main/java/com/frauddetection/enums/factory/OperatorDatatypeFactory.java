package com.frauddetection.enums.factory;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Optional;

import javax.annotation.PostConstruct;

import com.frauddetection.service.operationalfraud.OperatorDatatype;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public enum OperatorDatatypeFactory {

	GREATER_THAN_OPERATOR_DOUBLE(3, 2) {
		@Override
		public OperatorDatatype getOperatorDatatype() {
			log.info("Returing Operator: GREATER_THAN with datatype Double");
			return operatorDatatype;
		}
	},
	EQUAL_STRING(1, 3) {
		@Override
		public OperatorDatatype getOperatorDatatype() {
			log.info("Returing Operator: EQUAL with datatype String");
			return operatorDatatype;
		}
	},
	IN_STRING(5, 3) {
		@Override
		public OperatorDatatype getOperatorDatatype() {
			log.info("Returing Operator: IN with datatype String");
			return operatorDatatype;
		}
	},
	NOT_IN_STRING(6, 3) {
		@Override
		public OperatorDatatype getOperatorDatatype() {
			log.info("Returing Operator: NOT_IN with datatype String");
			return operatorDatatype;
		}
	};

	public OperatorDatatype operatorDatatype;

	private long dataTypeId;

	private long operatorId;

	OperatorDatatypeFactory(long operatorId, long dataTypeId) {
		this.operatorId = operatorId;
		this.dataTypeId = dataTypeId;
	}

	public long getDataTypeId() {
		return dataTypeId;
	}

	public long getOperatorId() {
		return operatorId;
	}

	public void setOperatorDatatype(OperatorDatatype operatorDatatype) {
		this.operatorDatatype = operatorDatatype;
	}

	public abstract OperatorDatatype getOperatorDatatype();

	public static OperatorDatatypeFactory getOperatorDataTypeEnum(long operatorId, long dataTypeId) {
		Optional<OperatorDatatypeFactory> operator = Arrays.stream(OperatorDatatypeFactory.values()).filter(o -> o.getDataTypeId() == dataTypeId).filter(d -> d.getOperatorId() == operatorId).findFirst();
		return operator.get();
	}

	@Component
	public static class setOperatorDatatype {

		@Autowired
		private OperatorDatatype greaterThanOperatorDouble;

		@Autowired
		private OperatorDatatype equalOperatorString;

		@Autowired
		private OperatorDatatype inOperatorString;

		@Autowired
		private OperatorDatatype notInOperatorString;

		@PostConstruct
		public void setOperatorDatatypeService() {
			log.info("Initialzing OperatorDataType Factory Objects By Spring Autowired!");
			for (OperatorDatatypeFactory op : EnumSet.allOf(OperatorDatatypeFactory.class)) {
				if (OperatorDatatypeFactory.GREATER_THAN_OPERATOR_DOUBLE.equals(op)) {
					log.info("OperatorDatype: GREATER_THAN_OPERATOR_DOUBLE initialized successfully");
					op.setOperatorDatatype(greaterThanOperatorDouble);
				} else if (OperatorDatatypeFactory.EQUAL_STRING.equals(op)) {
					log.info("OperatorDatype: EQUAL_STRING initialized successfully");
					op.setOperatorDatatype(equalOperatorString);
				} else if (OperatorDatatypeFactory.IN_STRING.equals(op)) {
					log.info("OperatorDatype: IN_STRING initialized successfully");
					op.setOperatorDatatype(inOperatorString);
				} else if (OperatorDatatypeFactory.NOT_IN_STRING.equals(op)) {
					log.info("OperatorDatype: NOT_IN_STRING initialized successfully");
					op.setOperatorDatatype(notInOperatorString);
				}
			}
		}
	}
}
