package com.pismo.core.transaction.domain.mapper;

import com.pismo.core.transaction.domain.dto.response.TransactionResponse;
import com.pismo.core.transaction.domain.entity.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {
    public static TransactionResponse toResponse(final Transaction transaction) {
        return TransactionResponse.builder()
                .transactionId(transaction.getTransactionId())
                .accountId(transaction.getAccountId())
                .operationTypeId(transaction.getOperationTypeId())
                .amount(transaction.getAmount())
                .build();
    }
}
