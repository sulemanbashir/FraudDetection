package com.frauddetection.dto;

import javax.validation.constraints.NotNull;

import com.frauddetection.validation.groups.OperationalFraudRuleInsertValidation;

public class OperatorDto {

	@NotNull(groups = OperationalFraudRuleInsertValidation.class)
	private Long id;

	private String name;

	private String description;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
