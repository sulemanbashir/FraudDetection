package com.frauddetection.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ResponseInfo(String status, String message, String fraudScore,
                           ViolationErrorResponse violationErrorResponse) {

    public static ResponseInfo empty() {
        return new ResponseInfo(null, null, "0", null);
    }

    public static ResponseInfo success(String status) {
        return new ResponseInfo(status, null, null, null);
    }

    public static ResponseInfo error(String status, ViolationErrorResponse violationErrorResponse) {
        return new ResponseInfo(status, null, null, violationErrorResponse);
    }
}
