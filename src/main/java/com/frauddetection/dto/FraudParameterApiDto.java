package com.frauddetection.dto;

import javax.validation.constraints.NotNull;

import com.frauddetection.validation.groups.OperationalFraudRuleInsertValidation;

public class FraudParameterApiDto {

	@NotNull(groups = OperationalFraudRuleInsertValidation.class)
	private Long id;

	private String name;

	private String description;

	private FraudTypeDto typeCode;

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

	public FraudTypeDto getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(FraudTypeDto typeCode) {
		this.typeCode = typeCode;
	}

	public String getClassDetail() {
		return classDetail;
	}

	public void setClassDetail(String classDetail) {
		this.classDetail = classDetail;
	}

}
