package com.frauddetection.dto;

import java.util.ArrayList;
import java.util.List;

public class ViolationErrorResponse {

	private List<ViolationDto> violations = new ArrayList<>();

	public List<ViolationDto> getViolations() {
		return violations;
	}

	public void setViolations(List<ViolationDto> violations) {
		this.violations = violations;
	}

}
