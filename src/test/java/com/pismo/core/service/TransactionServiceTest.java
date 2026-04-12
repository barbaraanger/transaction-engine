package com.pismo.core.service;

import com.pismo.core.domain.entity.Transaction;
import com.pismo.core.repository.TransactionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {
    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    @DisplayName("Should save transaction successfully")
    void shouldSaveTransaction() {
        Transaction transaction = new Transaction();

        when(transactionRepository.save(transaction)).thenReturn(transaction);

        Transaction result = transactionService.save(transaction);

        assertEquals(transaction, result);
        verify(transactionRepository).save(transaction);
    }
}
