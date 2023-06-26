package com.frauddetection.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseInfo {

	private String status;
	private String message;
	private String fraudScore;
	private ViolationErrorResponse violationErrorResponse;

	public ResponseInfo(String status, ViolationErrorResponse violationErrorResponse) {
		this.status = status;
		this.violationErrorResponse = violationErrorResponse;
	}

	public ResponseInfo(String status) {
		this.status = status;
	}

	public ResponseInfo() {
		fraudScore = "0";
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getFraudScore() {
		return fraudScore;
	}

	public void setFraudScore(String fraudScore) {
		this.fraudScore = fraudScore;
	}

	public ViolationErrorResponse getViolationErrorResponse() {
		return violationErrorResponse;
	}

	public void setViolationErrorResponse(ViolationErrorResponse violationErrorResponse) {
		this.violationErrorResponse = violationErrorResponse;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ResponseInfo [status=");
		builder.append(status);
		builder.append(", message=");
		builder.append(message);
		builder.append(", fraudScore=");
		builder.append(fraudScore);
		builder.append("]");
		return builder.toString();
	}

}
