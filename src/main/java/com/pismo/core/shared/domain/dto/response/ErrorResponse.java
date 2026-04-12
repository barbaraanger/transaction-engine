package com.pismo.core.shared.domain.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ErrorResponse(
        LocalDateTime timestamp,
        Integer statusCode,
        String errorMessage,
        String path
) {
}
