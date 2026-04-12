package com.pismo.core.controller.docs;

import com.pismo.core.domain.dto.request.CreateAccountRequest;
import com.pismo.core.domain.dto.response.AccountResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

public interface AccountControllerDocumentation {
    @Operation(summary = "Create a new account - document number must unique")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Account successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "409", description = "Account already exists")
    })
    ResponseEntity<AccountResponse> createAccount(
            @RequestBody(
                    description = "Account to be created with a given document number",
                    required = true,
                    content = @Content(schema = @Schema(implementation = CreateAccountRequest.class))
            )
            CreateAccountRequest createAccountRequest
    );

    @Operation(summary = "Get account by account ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account found"),
            @ApiResponse(responseCode = "404", description = "Account not found")
    })
    ResponseEntity<AccountResponse> getAccount(Long accountId);

}
