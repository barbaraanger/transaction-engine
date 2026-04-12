package com.pismo.core.domain.mapper;

import com.pismo.core.domain.dto.response.TransactionResponse;
import com.pismo.core.domain.entity.Transaction;
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
