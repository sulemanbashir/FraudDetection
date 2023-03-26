package com.frauddetection.rules.operational.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.frauddetection.rules.operational.validation.FraudRuleInsertValidation;
import com.frauddetection.rules.operational.validation.FraudRuleUpdateValidation;

public class FraudParameterDetailsDto {

	@NotBlank(groups = {FraudRuleInsertValidation.class, FraudRuleUpdateValidation.class})
	private String merchantName;

	@NotNull(groups = FraudRuleInsertValidation.class)
	private Integer fraudParameterApiId;

	@NotNull(groups = FraudRuleInsertValidation.class)
	private Integer entityId;

	@NotNull(groups = FraudRuleInsertValidation.class)
	private Integer operatorId;

	@NotNull(groups = FraudRuleInsertValidation.class)
	private Integer unitId;

	@NotBlank(groups = {FraudRuleInsertValidation.class, FraudRuleUpdateValidation.class})
	private String fraudRuleName;
	
	@NotNull(groups = FraudRuleInsertValidation.class)
	private Double fraudScore;


	private Integer terminalThreadThreshold;

	@NotBlank(groups = FraudRuleInsertValidation.class)
	private String fraudCriteriaValue1;


	private String fraudCriteriaValue2;


	private String sourceEntityTable;


	private String sourceEntityColumn;


	private Integer timeWindow;
	

	private String exchangeCode;
	

	private String toCurrCode;
	

	private String actualCriteriaValue;


	public String getMerchantName() {
		return merchantName;
	}


	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}


	public Integer getFraudParameterApiId() {
		return fraudParameterApiId;
	}


	public void setFraudParameterApiId(Integer fraudParameterApiId) {
		this.fraudParameterApiId = fraudParameterApiId;
	}


	public Integer getEntityId() {
		return entityId;
	}


	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}


	public Integer getOperatorId() {
		return operatorId;
	}


	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}


	public Integer getUnitId() {
		return unitId;
	}


	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}


	public String getFraudRuleName() {
		return fraudRuleName;
	}


	public void setFraudRuleName(String fraudRuleName) {
		this.fraudRuleName = fraudRuleName;
	}


	public Double getFraudScore() {
		return fraudScore;
	}


	public void setFraudScore(Double fraudScore) {
		this.fraudScore = fraudScore;
	}


	public Integer getTerminalThreadThreshold() {
		return terminalThreadThreshold;
	}


	public void setTerminalThreadThreshold(Integer terminalThreadThreshold) {
		this.terminalThreadThreshold = terminalThreadThreshold;
	}


	public String getFraudCriteriaValue1() {
		return fraudCriteriaValue1;
	}


	public void setFraudCriteriaValue1(String fraudCriteriaValue1) {
		this.fraudCriteriaValue1 = fraudCriteriaValue1;
	}


	public String getFraudCriteriaValue2() {
		return fraudCriteriaValue2;
	}


	public void setFraudCriteriaValue2(String fraudCriteriaValue2) {
		this.fraudCriteriaValue2 = fraudCriteriaValue2;
	}


	public String getSourceEntityTable() {
		return sourceEntityTable;
	}


	public void setSourceEntityTable(String sourceEntityTable) {
		this.sourceEntityTable = sourceEntityTable;
	}


	public String getSourceEntityColumn() {
		return sourceEntityColumn;
	}


	public void setSourceEntityColumn(String sourceEntityColumn) {
		this.sourceEntityColumn = sourceEntityColumn;
	}


	public Integer getTimeWindow() {
		return timeWindow;
	}


	public void setTimeWindow(Integer timeWindow) {
		this.timeWindow = timeWindow;
	}


	public String getExchangeCode() {
		return exchangeCode;
	}


	public void setExchangeCode(String exchangeCode) {
		this.exchangeCode = exchangeCode;
	}


	public String getToCurrCode() {
		return toCurrCode;
	}


	public void setToCurrCode(String toCurrCode) {
		this.toCurrCode = toCurrCode;
	}


	public String getActualCriteriaValue() {
		return actualCriteriaValue;
	}


	public void setActualCriteriaValue(String actualCriteriaValue) {
		this.actualCriteriaValue = actualCriteriaValue;
	}

}
