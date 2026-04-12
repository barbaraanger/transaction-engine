package com.pismo.core.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(name = "AccountResponse", description = "Response payload for an account")
public record AccountResponse(
        @JsonProperty("account_id")
        @Schema(description = "Unique account identifier", example = "1")
        Long accountId,

        @JsonProperty("document_number")
        @Schema(
                description = "Customer document number, document number is unique",
                example = "12345678900"
        )
        String documentNumber
) {
}