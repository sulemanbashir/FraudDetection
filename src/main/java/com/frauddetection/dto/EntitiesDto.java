package com.frauddetection.dto;

import javax.validation.constraints.NotNull;

import com.frauddetection.validation.groups.OperationalFraudRuleInsertValidation;

public class EntitiesDto {

	@NotNull(groups = OperationalFraudRuleInsertValidation.class)
	private Long id;

	private String name;

	private DataTypeDto datatype;

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

	public DataTypeDto getDatatype() {
		return datatype;
	}

	public void setDatatype(DataTypeDto datatype) {
		this.datatype = datatype;
	}

}
