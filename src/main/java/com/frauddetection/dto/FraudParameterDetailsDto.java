package com.frauddetection.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.frauddetection.validation.groups.OperationalFraudRuleInsertValidation;
import com.frauddetection.validation.groups.OperationalFraudRuleUpdateValidation;

public class FraudParameterDetailsDto {

	private Long id;

	@Valid
	@NotNull(groups = { OperationalFraudRuleInsertValidation.class, OperationalFraudRuleUpdateValidation.class })
	private MerchantDto merchant;

	@Valid
	@NotNull(groups = { OperationalFraudRuleInsertValidation.class })
	private FraudParameterApiDto fraudParameterApi;

	@Valid
	@NotNull(groups = { OperationalFraudRuleInsertValidation.class })
	private EntitiesDto entity;

	@Valid
	@NotNull(groups = { OperationalFraudRuleInsertValidation.class })
	private OperatorDto operator;

	private UnitDto unit;

	@NotBlank(groups = { OperationalFraudRuleInsertValidation.class, OperationalFraudRuleUpdateValidation.class })
	private String fraudRuleName;

	@NotNull(groups = OperationalFraudRuleInsertValidation.class)
	private Double fraudScore;

	private Integer terminalThreadThreshold;

	@NotBlank(groups = OperationalFraudRuleInsertValidation.class)
	private String fraudCriteriaValue1;

	private String fraudCriteriaValue2;

	private String sourceEntityTable;

	private String sourceEntityColumn;

	private Integer timeWindow;

	private String exchangeCode;

	private String toCurrCode;

	private String actualCriteriaValue;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MerchantDto getMerchant() {
		return merchant;
	}

	public void setMerchant(MerchantDto merchant) {
		this.merchant = merchant;
	}

	public FraudParameterApiDto getFraudParameterApi() {
		return fraudParameterApi;
	}

	public void setFraudParameterApi(FraudParameterApiDto fraudParameterApi) {
		this.fraudParameterApi = fraudParameterApi;
	}

	public EntitiesDto getEntity() {
		return entity;
	}

	public void setEntity(EntitiesDto entity) {
		this.entity = entity;
	}

	public OperatorDto getOperator() {
		return operator;
	}

	public void setOperator(OperatorDto operator) {
		this.operator = operator;
	}

	public UnitDto getUnit() {
		return unit;
	}

	public void setUnit(UnitDto unit) {
		this.unit = unit;
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
