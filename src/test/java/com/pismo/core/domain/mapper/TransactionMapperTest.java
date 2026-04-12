package com.pismo.core.domain.mapper;

import com.pismo.core.domain.dto.response.TransactionResponse;
import com.pismo.core.domain.entity.Transaction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.pismo.core.Stubs.ACCOUNT_ID;
import static com.pismo.core.Stubs.AMOUNT;
import static com.pismo.core.Stubs.OPERATION_TYPE_ID;
import static com.pismo.core.Stubs.TRANSACTION_ID;
import static org.junit.jupiter.api.Assertions.*;

public class TransactionMapperTest {
    @Test
    @DisplayName("Should map Transaction entity to TransactionResponse")
    void shouldMapTransactionToTransactionResponse() {
        Transaction transaction = new Transaction(TRANSACTION_ID, ACCOUNT_ID, OPERATION_TYPE_ID, AMOUNT, LocalDateTime.now());

        TransactionResponse response = TransactionMapper.toResponse(transaction);

        assertNotNull(response);
        assertInstanceOf(TransactionResponse.class, response);
        assertEquals(TRANSACTION_ID, response.transactionId());
        assertEquals(ACCOUNT_ID, response.accountId());
        assertEquals(OPERATION_TYPE_ID, response.operationTypeId());
        assertEquals(AMOUNT, response.amount());
    }
}


