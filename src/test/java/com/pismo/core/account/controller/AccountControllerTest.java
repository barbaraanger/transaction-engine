package com.pismo.core.account.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pismo.core.account.domain.dto.request.CreateAccountRequest;
import com.pismo.core.account.domain.exception.AccountNotFoundException;
import com.pismo.core.account.domain.exception.ResourceAlreadyExistsException;
import com.pismo.core.account.domain.entity.Account;
import com.pismo.core.account.usecase.AccountUsecase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static com.pismo.core.Stubs.ACCOUNT_NOT_FOUND_ERROR;
import static com.pismo.core.Stubs.DOCUMENT_NUMBER_DOCUMENT_NUMBER_IS_REQUIRED_ERROR;
import static com.pismo.core.Stubs.RESOURCE_ALREADY_EXISTS_ERROR;
import static com.pismo.core.Stubs.ACCOUNT_ID;
import static com.pismo.core.Stubs.DOCUMENT_NUMBER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
public class AccountControllerTest {
    private static final String ACCOUNTS_ENDPOINT = "/accounts";
    private static final String ACCOUNT_BY_ID_ENDPOINT = "/accounts/{accountId}";

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AccountUsecase accountUsecase;

    @Test
    @DisplayName("Should create account and return 201 status with account data")
    void shouldCreateAccountAndReturn201() throws Exception {
        CreateAccountRequest request = CreateAccountRequest.builder()
                .documentNumber(DOCUMENT_NUMBER)
                .build();
        Account account = Account.builder()
                .accountId(ACCOUNT_ID)
                .documentNumber(DOCUMENT_NUMBER)
                .build();

        when(accountUsecase.createAccount(any(CreateAccountRequest.class))).thenReturn(account);

        mockMvc.perform(post(ACCOUNTS_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.account_id").value(ACCOUNT_ID))
                .andExpect(jsonPath("$.document_number").value(DOCUMENT_NUMBER));
    }

    @Test
    @DisplayName("Should return 400 when document_number is blank")
    void shouldReturn400WhenDocumentNumberIsBlank() throws Exception {
        mockMvc.perform(post(ACCOUNTS_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CreateAccountRequest.builder()
                                .documentNumber("")
                                .build())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value(DOCUMENT_NUMBER_DOCUMENT_NUMBER_IS_REQUIRED_ERROR));
    }

    @Test
    @DisplayName("Should return 400 when document_number is missing")
    void shouldReturn400WhenDocumentNumberIsMissing() throws Exception {
        mockMvc.perform(post(ACCOUNTS_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CreateAccountRequest.builder().build())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value(DOCUMENT_NUMBER_DOCUMENT_NUMBER_IS_REQUIRED_ERROR));
    }

    @Test
    @DisplayName("Should get account by id and return 200 status with account data")
    void shouldGetAccountByIdAndReturn200() throws Exception {
        Account account = Account.builder()
                .accountId(ACCOUNT_ID)
                .documentNumber(DOCUMENT_NUMBER)
                .build();

        when(accountUsecase.getAccountById(ACCOUNT_ID)).thenReturn(account);

        mockMvc.perform(get(ACCOUNT_BY_ID_ENDPOINT, ACCOUNT_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.account_id").value(ACCOUNT_ID))
                .andExpect(jsonPath("$.document_number").value(DOCUMENT_NUMBER));
    }

    @Test
    @DisplayName("Should return 404 when account is not found by id")
    void shouldReturn404WhenAccountNotFoundById() throws Exception {
        when(accountUsecase.getAccountById(ACCOUNT_ID))
                .thenThrow(new AccountNotFoundException(ACCOUNT_ID));

        mockMvc.perform(get(ACCOUNT_BY_ID_ENDPOINT, ACCOUNT_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMessage").value(ACCOUNT_NOT_FOUND_ERROR.formatted(ACCOUNT_ID)));
    }

    @Test
    @DisplayName("Should return 409 when trying to create account with existing document number")
    void shouldReturn409WhenAccountAlreadyExists() throws Exception {
        CreateAccountRequest request = CreateAccountRequest.builder()
                .documentNumber(DOCUMENT_NUMBER)
                .build();

        when(accountUsecase.createAccount(any(CreateAccountRequest.class)))
                .thenThrow(new ResourceAlreadyExistsException(RESOURCE_ALREADY_EXISTS_ERROR.formatted(DOCUMENT_NUMBER)));

        mockMvc.perform(post(ACCOUNTS_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.errorMessage").value(RESOURCE_ALREADY_EXISTS_ERROR.formatted(DOCUMENT_NUMBER)));
    }
}



