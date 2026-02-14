package com.frauddetection.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "b_fraud_param_api")
public class FraudParameterApi extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "f_type_code", referencedColumnName = "f_type_code")
    private FraudType typeCode;

    @Column(name = "class_detail")
    private String classDetail;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public FraudType getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(FraudType typeCode) {
        this.typeCode = typeCode;
    }

    public String getClassDetail() {
        return classDetail;
    }

    public void setClassDetail(String classDetail) {
        this.classDetail = classDetail;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("FraudParameterApi [id=");
        builder.append(id);
        builder.append(", name=");
        builder.append(name);
        builder.append(", description=");
        builder.append(description);
        builder.append(", typeCode=");
        builder.append(typeCode);
        builder.append(", classDetail=");
        builder.append(classDetail);
        builder.append("]");
        return builder.toString();
    }
}
