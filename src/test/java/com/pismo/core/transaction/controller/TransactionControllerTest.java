package com.pismo.core.transaction.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pismo.core.transaction.domain.dto.request.TransactionRequest;
import com.pismo.core.transaction.domain.entity.Transaction;
import com.pismo.core.account.domain.exception.AccountNotFoundException;
import com.pismo.core.transaction.controller.TransactionController;
import com.pismo.core.transaction.usecase.TransactionUsecase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.pismo.core.Stubs.AMOUNT_IS_REQUIRED_ERROR;
import static com.pismo.core.Stubs.OPERATION_TYPE_ID_IS_REQUIRED_ERROR;
import static com.pismo.core.Stubs.OPERATION_TYPE_INVALID_ERROR;
import static com.pismo.core.Stubs.ACCOUNT_ID_IS_REQUIRED_ERROR;
import static com.pismo.core.Stubs.ACCOUNT_NOT_FOUND_ERROR;
import static com.pismo.core.Stubs.AMOUNT_MUST_BE_POSITIVE_ERROR;
import static com.pismo.core.Stubs.AMOUNT_MUST_BE_A_VALID_MONETARY_VALUE_ERROR;
import static com.pismo.core.Stubs.ACCOUNT_ID;
import static com.pismo.core.Stubs.TRANSACTION_ID;
import static com.pismo.core.Stubs.PAYMENT_OPERATION_TYPE_ID;
import static com.pismo.core.Stubs.PURCHASE_OPERATION_TYPE_ID;
import static com.pismo.core.Stubs.INVALID_OPERATION_TYPE_ID;
import static com.pismo.core.Stubs.AMOUNT;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {
    private static final String TRANSACTIONS_ENDPOINT = "/transactions";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TransactionUsecase transactionUsecase;

    @Test
    @DisplayName("Should process transaction successfully for purchase and return 201 status")
    void shouldProcessTransactionSuccessfullyForPurchaseAndReturn201() throws Exception {
        String content = createRequestTransactionJsonWithOperationId(PURCHASE_OPERATION_TYPE_ID);
        Transaction transaction = new Transaction(TRANSACTION_ID, ACCOUNT_ID, PURCHASE_OPERATION_TYPE_ID, AMOUNT.negate(), LocalDateTime.now());

        when(transactionUsecase.processTransaction(any(TransactionRequest.class))).thenReturn(transaction);

        mockMvc.perform(post(TRANSACTIONS_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.transaction_id").value(TRANSACTION_ID))
                .andExpect(jsonPath("$.account_id").value(ACCOUNT_ID))
                .andExpect(jsonPath("$.operation_type_id").value(PURCHASE_OPERATION_TYPE_ID))
                .andExpect(jsonPath("$.amount").value(AMOUNT.negate()));
    }

    @Test
    @DisplayName("Should process transaction successfully for payment and return 201 status")
    void shouldProcessTransactionSuccessfullyForPaymentAndReturn201() throws Exception {
        String content = createRequestTransactionJsonWithOperationId(PAYMENT_OPERATION_TYPE_ID);
        Transaction transaction = new Transaction(TRANSACTION_ID, ACCOUNT_ID, PAYMENT_OPERATION_TYPE_ID, AMOUNT, LocalDateTime.now());

        when(transactionUsecase.processTransaction(any(TransactionRequest.class))).thenReturn(transaction);

        mockMvc.perform(post(TRANSACTIONS_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.transaction_id").value(TRANSACTION_ID))
                .andExpect(jsonPath("$.account_id").value(ACCOUNT_ID))
                .andExpect(jsonPath("$.operation_type_id").value(PAYMENT_OPERATION_TYPE_ID))
                .andExpect(jsonPath("$.amount").value(AMOUNT));
    }

    @Test
    @DisplayName("Should return 400 when account_id is missing")
    void shouldReturn400WhenAccountIdIsMissing() throws Exception {
        String content = createTransactionRequestJson(TransactionRequest.builder()
                .operationTypeId(PURCHASE_OPERATION_TYPE_ID)
                .amount(AMOUNT)
                .build());

        mockMvc.perform(post(TRANSACTIONS_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value(ACCOUNT_ID_IS_REQUIRED_ERROR));
    }

    @Test
    @DisplayName("Should return 422 when operation_type_id is missing")
    void shouldReturn422WhenOperationTypeIdIsInvalid() throws Exception {
        String content = createTransactionRequestJson(TransactionRequest.builder()
                .accountId(ACCOUNT_ID)
                .operationTypeId(INVALID_OPERATION_TYPE_ID)
                .amount(AMOUNT)
                .build());

        mockMvc.perform(post(TRANSACTIONS_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isUnprocessableContent())
                .andExpect(jsonPath("$.errorMessage").value(OPERATION_TYPE_INVALID_ERROR));
    }

    @Test
    @DisplayName("Should return 400 when operation_type_id is missing")
    void shouldReturn400WhenOperationTypeIdIsMissing() throws Exception {
        String content = createTransactionRequestJson(TransactionRequest.builder()
                .accountId(ACCOUNT_ID)
                .amount(AMOUNT)
                .build());

        mockMvc.perform(post(TRANSACTIONS_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value(OPERATION_TYPE_ID_IS_REQUIRED_ERROR));
    }

    @Test
    @DisplayName("Should return 400 when amount is missing")
    void shouldReturn400WhenAmountIsMissing() throws Exception {
        String content = createTransactionRequestJson(TransactionRequest.builder()
                .accountId(ACCOUNT_ID)
                .operationTypeId(PURCHASE_OPERATION_TYPE_ID)
                .build());

        mockMvc.perform(post(TRANSACTIONS_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value(AMOUNT_IS_REQUIRED_ERROR));
    }

    @Test
    @DisplayName("Should return 400 when amount is negative")
    void shouldReturn400WhenAmountIsNegative() throws Exception {
        String content = createTransactionRequestJson(TransactionRequest.builder()
                .accountId(ACCOUNT_ID)
                .operationTypeId(PURCHASE_OPERATION_TYPE_ID)
                .amount(BigDecimal.valueOf(-10.0))
                .build());

        mockMvc.perform(post(TRANSACTIONS_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value(AMOUNT_MUST_BE_POSITIVE_ERROR));
    }

    @Test
    @DisplayName("Should return 404 when account is not found")
    void shouldReturn404WhenAccountNotFound() throws Exception {
        String content = createRequestTransactionJsonWithOperationId(PURCHASE_OPERATION_TYPE_ID);

        when(transactionUsecase.processTransaction(any(TransactionRequest.class)))
                .thenThrow(new AccountNotFoundException(ACCOUNT_ID));

        mockMvc.perform(post(TRANSACTIONS_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMessage").value(ACCOUNT_NOT_FOUND_ERROR.formatted(ACCOUNT_ID)));
    }

    @Test
    @DisplayName("Should return 400 when operation type is invalid")
    void shouldReturn400WhenOperationTypeInvalid() throws Exception {
        String content = createTransactionRequestJson(TransactionRequest.builder()
                .accountId(ACCOUNT_ID)
                .operationTypeId(INVALID_OPERATION_TYPE_ID)
                .amount(AMOUNT)
                .build());

        mockMvc.perform(post(TRANSACTIONS_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isUnprocessableContent())
                .andExpect(jsonPath("$.errorMessage").value(OPERATION_TYPE_INVALID_ERROR));
    }

    @Test
    @DisplayName("Should return 400 when amount is a invalid monetary value")
    void shouldReturn400WhenAmountIsInvalidMonetaryValue() throws Exception {
        String content = createTransactionRequestJson(TransactionRequest.builder()
                .accountId(ACCOUNT_ID)
                .operationTypeId(PURCHASE_OPERATION_TYPE_ID)
                .amount(new BigDecimal("10.012"))
                .build());

        mockMvc.perform(post(TRANSACTIONS_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value(AMOUNT_MUST_BE_A_VALID_MONETARY_VALUE_ERROR));
    }

    private String createRequestTransactionJsonWithOperationId(Integer operationTypeId) throws Exception {
        return createTransactionRequestJson(TransactionRequest.builder()
                .accountId(ACCOUNT_ID)
                .operationTypeId(operationTypeId)
                .amount(AMOUNT)
                .build());
    }

    private String createTransactionRequestJson(TransactionRequest transactionRequest) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(transactionRequest);
    }

}

