package com.pismo.core.transaction.controller.docs;

import com.pismo.core.transaction.domain.dto.request.TransactionRequest;
import com.pismo.core.transaction.domain.dto.response.TransactionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

public interface TransactionControllerDocumentation {
    @Operation(
            summary = "Create a new transaction",
            description = "Processes a financial transaction based on account id, operation type and amount"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transaction successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Account not found"),
            @ApiResponse(responseCode = "422", description = "Invalid operation type")
    })
    ResponseEntity<TransactionResponse> create(TransactionRequest request);
}
