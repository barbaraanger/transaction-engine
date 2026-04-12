package com.pismo.core.transaction.service;

import com.pismo.core.transaction.domain.entity.Transaction;
import com.pismo.core.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public Transaction save(final Transaction transaction) {
        return transactionRepository.save(transaction);
    }

}
