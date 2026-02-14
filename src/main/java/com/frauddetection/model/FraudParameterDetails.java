package com.frauddetection.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.frauddetection.validation.groups.BasicInfoValidator;
import com.frauddetection.validation.groups.TerminalThreatScoreValidator;

@Entity
@Table(name = "fraud_param_details")
public class FraudParameterDetails extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "merchant", referencedColumnName = "name")
    private Merchant merchant;

    @ManyToOne
    @JoinColumn(name = "f_param_api_id", referencedColumnName = "id")
    private FraudParameterApi fraudParameterApi;

    @ManyToOne
    @JoinColumn(name = "fraud_entity_id", referencedColumnName = "id")
    private Entities entity;

    @ManyToOne
    @JoinColumn(name = "operator_id", referencedColumnName = "id")
    private Operator operator;

    @ManyToOne
    @JoinColumn(name = "unit", referencedColumnName = "name")
    private Unit unit;

    @Column(name = "fraud_rule_name")
    private String fraudRuleName;

    @Column(name = "fraud_score")
    private Double fraudScore;

    @NotNull(groups = TerminalThreatScoreValidator.class)
    @Column(name = "terminal_threat_threshold")
    private Integer terminalThreatThreshold;

    @Column(name = "fraud_criteria_value1")
    private String fraudCriteriaValue1;

    @Column(name = "fraud_criteria_value2")
    private String fraudCriteriaValue2;

    @Column(name = "src_entity_table")
    private String sourceEntityTable;

    @Column(name = "src_entity_column")
    private String sourceEntityColumn;

    @Column(name = "time_window")
    private Integer timeWindow;

    @Column(name = "exchange_code")
    private String exchangeCode;

    @Column(name = "to_curr_code")
    private String toCurrCode;

    @NotBlank(groups = BasicInfoValidator.class)
    @Column(name = "actual_criteria_value")
    private String actualCriteriaValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public Entities getEntity() {
        return entity;
    }

    public void setEntity(Entities entity) {
        this.entity = entity;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Double getFraudScore() {
        return fraudScore;
    }

    public void setFraudScore(Double fraudScore) {
        this.fraudScore = fraudScore;
    }

    public Integer getTerminalThreatThreshold() {
        return terminalThreatThreshold;
    }

    public void setTerminalThreatThreshold(Integer terminalThreatThreshold) {
        this.terminalThreatThreshold = terminalThreatThreshold;
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

    public FraudParameterApi getFraudParameterApi() {
        return fraudParameterApi;
    }

    public void setFraudParameterApi(FraudParameterApi fraudParameterApi) {
        this.fraudParameterApi = fraudParameterApi;
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

    public String getFraudRuleName() {
        return fraudRuleName;
    }

    public void setFraudRuleName(String fraudRuleName) {
        this.fraudRuleName = fraudRuleName;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("FraudParameterDetails [id=");
        builder.append(id);
        builder.append(", merchant=");
        builder.append(merchant);
        builder.append(", fraudParameterApi=");
        builder.append(fraudParameterApi);
        builder.append(", entity=");
        builder.append(entity);
        builder.append(", operator=");
        builder.append(operator);
        builder.append(", unit=");
        builder.append(unit);
        builder.append(", fraudScore=");
        builder.append(fraudScore);
        builder.append(", terminalThreatThreshold=");
        builder.append(terminalThreatThreshold);
        builder.append(", sourceEntityTable=");
        builder.append(sourceEntityTable);
        builder.append(", sourceEntityColumn=");
        builder.append(sourceEntityColumn);
        builder.append(", timeWindow=");
        builder.append(timeWindow);
        builder.append(", exchangeCode=");
        builder.append(exchangeCode);
        builder.append(", toCurrCode=");
        builder.append(toCurrCode);
        builder.append(", fraudRuleName=");
        builder.append(fraudRuleName);
        builder.append("]");
        return builder.toString();
    }

}
