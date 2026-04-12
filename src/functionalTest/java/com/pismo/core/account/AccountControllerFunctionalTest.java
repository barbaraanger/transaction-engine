package com.pismo.core.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pismo.core.CoreApplication;
import com.pismo.core.account.domain.dto.request.CreateAccountRequest;
import com.pismo.core.account.domain.entity.Account;
import com.pismo.core.account.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = CoreApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("functional-test")
@DisplayName("Account Controller Functional Tests")
class AccountControllerFunctionalTest {
    private static final String ACCOUNTS_ENDPOINT = "/accounts";
    private static final String DOCUMENT_NUMBER = "12345678901";

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        accountRepository.deleteAll();
    }

    @Test
    @DisplayName("Should create account and persist to database")
    void shouldCreateAccountAndPersistToDatabase() throws Exception {
        final CreateAccountRequest request = CreateAccountRequest.builder()
                .documentNumber(DOCUMENT_NUMBER)
                .build();

        final String response = mockMvc.perform(post(ACCOUNTS_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.document_number").value(DOCUMENT_NUMBER))
                .andReturn()
                .getResponse()
                .getContentAsString();

        final Long accountId = objectMapper.readTree(response).get("account_id").asLong();

        assertThat(accountRepository.findById(accountId))
                .isPresent()
                .hasValueSatisfying(account -> {
                    assertThat(account.getDocumentNumber()).isEqualTo(DOCUMENT_NUMBER);
                    assertThat(account.getAccountId()).isEqualTo(accountId);
                });
    }

    @Test
    @DisplayName("Should get account by id from database")
    void shouldGetAccountByIdFromDatabase() throws Exception {
        final Account account = accountRepository.save(Account.builder()
                .documentNumber(DOCUMENT_NUMBER)
                .build());

        mockMvc.perform(get(ACCOUNTS_ENDPOINT + "/{accountId}", account.getAccountId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.account_id").value(account.getAccountId()))
                .andExpect(jsonPath("$.document_number").value(DOCUMENT_NUMBER));
    }

    @Test
    @DisplayName("Should prevent duplicate account creation with same document number")
    void shouldPreventDuplicateAccountCreation() throws Exception {
        final CreateAccountRequest request = CreateAccountRequest.builder()
                .documentNumber(DOCUMENT_NUMBER)
                .build();

        mockMvc.perform(post(ACCOUNTS_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        mockMvc.perform(post(ACCOUNTS_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());

        assertThat(accountRepository.findAll()).hasSize(1);
    }
}



