package com.pismo.core.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pismo.core.domain.validation.ValidOperationType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record TransactionRequest(
        @NotNull(message = "Account ID is required")
        @JsonProperty("account_id")
        @Schema(description = "ID of the account associated with the transaction", example = "1")
        Long accountId,

        @NotNull(message = "Operation Type ID is required")
        @ValidOperationType
        @JsonProperty("operation_type_id")
        @Schema(description = "ID of the operation type (1: Purchase, 2: Installment Purchase, 3: Withdrawal, 4: Payment)", example = "1")
        Integer operationTypeId,

        @NotNull(message = "Amount is required")
        @Positive(message = "Amount must be positive")
        @Schema(description = "Transaction amount. For purchases and withdrawals, the amount should be positive and will be negated in processing. For payments, the amount should be positive.", example = "100.00")
        @Digits(integer = 10, fraction = 2, message = "Amount must be a valid monetary value with up to 10 digits and 2 decimal places")
        /* Decided this number... */
        BigDecimal amount
        ) {
}
