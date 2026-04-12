package com.pismo.core.transaction.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record TransactionResponse(
        @JsonProperty("transaction_id")
        @Schema(description = "Unique transaction identifier", example = "1")
        Long transactionId,

        @JsonProperty("account_id")
        @Schema(description = "Account identifier, for an existing account", example = "1")
        Long accountId,

        @JsonProperty("operation_type_id")
        @Schema(description = "Type of operation", example = "1")
        Integer operationTypeId,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0.00")
        @JsonProperty("amount")
        @Schema(
                description = "Transaction amount (negative for debits, positive for credits)",
                example = "-75.50"
        )
        BigDecimal amount
) {
}
