package com.frauddetection.dto;

import javax.validation.constraints.NotBlank;

import com.frauddetection.validation.groups.OperationalFraudRuleInsertValidation;
import com.frauddetection.validation.groups.OperationalFraudRuleUpdateValidation;

public class MerchantDto {

	private Long id;

	@NotBlank(groups = { OperationalFraudRuleInsertValidation.class, OperationalFraudRuleUpdateValidation.class })
	private String name;

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

}
