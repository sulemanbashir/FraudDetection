package com.frauddetection.dto;

import java.util.List;

public record ViolationErrorResponse(List<ViolationDto> violations) {
}
