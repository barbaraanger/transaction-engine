package com.pismo.core.domain.mapper;

import com.pismo.core.domain.dto.response.AccountResponse;
import com.pismo.core.domain.entity.Account;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.pismo.core.Stubs.ACCOUNT_ID;
import static com.pismo.core.Stubs.DOCUMENT_NUMBER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class AccountMapperTest {
    @Test
    @DisplayName("Should map Account entity to AccountResponse")
    void shouldMapAccountToAccountResponse() {
        Account account = Account.builder()
                .accountId(ACCOUNT_ID)
                .documentNumber(DOCUMENT_NUMBER)
                .build();

        AccountResponse response = AccountMapper.toResponse(account);

        assertNotNull(response);
        assertInstanceOf(AccountResponse.class, response);
        assertEquals(ACCOUNT_ID, response.accountId());
        assertEquals(DOCUMENT_NUMBER, response.documentNumber());
    }
}


