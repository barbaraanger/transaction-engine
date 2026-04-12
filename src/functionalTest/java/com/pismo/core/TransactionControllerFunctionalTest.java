package com.pismo.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pismo.core.domain.dto.request.TransactionRequest;
import com.pismo.core.domain.entity.Account;
import com.pismo.core.repository.AccountRepository;
import com.pismo.core.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = CoreApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("functional-test")
@DisplayName("Transaction Controller Functional Tests")
class TransactionControllerFunctionalTest {
    private static final String TRANSACTIONS_ENDPOINT = "/transactions";
    private static final String DOCUMENT_NUMBER = "98765432109";
    private static final Integer PURCHASE_OPERATION_TYPE_ID = 1;
    private static final Integer PAYMENT_OPERATION_TYPE_ID = 4;
    private static final BigDecimal PURCHASE_AMOUNT = new BigDecimal("100.00");
    private static final BigDecimal PAYMENT_AMOUNT = new BigDecimal("50.00");

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    private Long accountId;

    @BeforeEach
    void setUp() {
        accountRepository.deleteAll();
        transactionRepository.deleteAll();
        final Account account = accountRepository.save(Account.builder().documentNumber(DOCUMENT_NUMBER).build());
        accountId = account.getAccountId();
    }

    @Test
    @DisplayName("Should create purchase transaction and persist to database")
    void shouldCreatePurchaseTransactionAndPersistToDatabase() throws Exception {
        final TransactionRequest request = TransactionRequest.builder()
                .accountId(accountId)
                .operationTypeId(PURCHASE_OPERATION_TYPE_ID)
                .amount(PURCHASE_AMOUNT)
                .build();

        final String response = mockMvc.perform(post(TRANSACTIONS_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.account_id").value(accountId))
                .andExpect(jsonPath("$.operation_type_id").value(PURCHASE_OPERATION_TYPE_ID))
                .andExpect(jsonPath("$.amount").value(-100.00))  // Purchase negates amount
                .andReturn()
                .getResponse()
                .getContentAsString();

        final Long transactionId = objectMapper.readTree(response).get("transaction_id").asLong();

        assertThat(transactionRepository.findById(transactionId))
                .isPresent()
                .hasValueSatisfying(transaction -> {
                    assertThat(transaction.getAccountId()).isEqualTo(accountId);
                    assertThat(transaction.getOperationTypeId()).isEqualTo(PURCHASE_OPERATION_TYPE_ID);
                    assertThat(transaction.getAmount()).isEqualTo(new BigDecimal("-100.00"));
                });
    }

    @Test
    @DisplayName("Should create payment transaction and persist to database")
    void shouldCreatePaymentTransactionAndPersistToDatabase() throws Exception {
        final TransactionRequest request = TransactionRequest.builder()
                .accountId(accountId)
                .operationTypeId(PAYMENT_OPERATION_TYPE_ID)
                .amount(PAYMENT_AMOUNT)
                .build();

        final String response = mockMvc.perform(post(TRANSACTIONS_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.account_id").value(accountId))
                .andExpect(jsonPath("$.operation_type_id").value(PAYMENT_OPERATION_TYPE_ID))
                .andExpect(jsonPath("$.amount").value(PAYMENT_AMOUNT))  // Payment keeps amount positive
                .andReturn()
                .getResponse()
                .getContentAsString();

        final Long transactionId = objectMapper.readTree(response).get("transaction_id").asLong();

        assertThat(transactionRepository.findById(transactionId))
                .isPresent()
                .hasValueSatisfying(transaction -> {
                    assertThat(transaction.getAccountId()).isEqualTo(accountId);
                    assertThat(transaction.getOperationTypeId()).isEqualTo(PAYMENT_OPERATION_TYPE_ID);
                    assertThat(transaction.getAmount()).isEqualTo(PAYMENT_AMOUNT);
                });
    }
}


