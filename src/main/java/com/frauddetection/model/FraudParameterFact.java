package com.frauddetection.model;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "fraud_param_fact")
public class FraudParameterFact extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "f_entity_id")
    private Long fraudEntityId;

    @Column(name = "f_entity_value")
    private String fraudEntityValue;

    @Column(name = "f_param")
    private String fraudParameter;

    @Column(name = "f_param_value")
    private String fraudParameterValue;

    @Column(name = "start_dtime")
    private Instant startDateTime;

    @Column(name = "is_unique")
    private Character isUnique;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getFraudEntityId() {
        return fraudEntityId;
    }

    public void setFraudEntityId(long fraudEntityId) {
        this.fraudEntityId = fraudEntityId;
    }

    public String getFraudEntityValue() {
        return fraudEntityValue;
    }

    public void setFraudEntityValue(String fraudEntityValue) {
        this.fraudEntityValue = fraudEntityValue;
    }

    public String getFraudParameter() {
        return fraudParameter;
    }

    public void setFraudParameter(String fraudParameter) {
        this.fraudParameter = fraudParameter;
    }

    public String getFraudParameterValue() {
        return fraudParameterValue;
    }

    public void setFraudParameterValue(String fraudParameterValue) {
        this.fraudParameterValue = fraudParameterValue;
    }

    public Instant getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Instant startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Character getIsUnique() {
        return isUnique;
    }

    public void setIsUnique(Character isUnique) {
        this.isUnique = isUnique;
    }
}
