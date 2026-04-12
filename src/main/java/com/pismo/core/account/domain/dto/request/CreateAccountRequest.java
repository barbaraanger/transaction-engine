package com.pismo.core.account.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CreateAccountRequest(
        @NotBlank(message = "Document number is required")
        @JsonProperty("document_number")
        @Schema(description = "Customer document number", example = "12345678900")
        String documentNumber
) {
}
