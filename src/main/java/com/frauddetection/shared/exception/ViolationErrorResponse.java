package com.frauddetection.shared.exception;

import java.util.List;

public record ViolationErrorResponse(List<ViolationDto> violations) {
}
