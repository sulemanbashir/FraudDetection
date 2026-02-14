package com.frauddetection.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "fraud_score_level")
public class FraudScoreLevel extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "score_lvl")
    private String scoreLevel;

    @Column(name = "name")
    private String name;

    @Column(name = "min_range")
    private Double minimunRange;

    @Column(name = "max_range")
    private Double maximumRange;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getScoreLevel() {
        return scoreLevel;
    }

    public void setScoreLevel(String scoreLevel) {
        this.scoreLevel = scoreLevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getMinimunRange() {
        return minimunRange;
    }

    public void setMinimunRange(Double minimunRange) {
        this.minimunRange = minimunRange;
    }

    public Double getMaximumRange() {
        return maximumRange;
    }

    public void setMaximumRange(Double maximumRange) {
        this.maximumRange = maximumRange;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("FraudScoreLevel [id=");
        builder.append(id);
        builder.append(", scoreLevel=");
        builder.append(scoreLevel);
        builder.append(", name=");
        builder.append(name);
        builder.append(", minimunRange=");
        builder.append(minimunRange);
        builder.append(", maximumRange=");
        builder.append(maximumRange);
        builder.append("]");
        return builder.toString();
    }
}
