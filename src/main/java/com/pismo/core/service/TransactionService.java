package com.pismo.core.service;

import com.pismo.core.domain.entity.Transaction;
import com.pismo.core.repository.TransactionRepository;
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
