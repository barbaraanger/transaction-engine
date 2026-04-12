package com.pismo.core.transaction.usecase;

import com.pismo.core.transaction.domain.dto.request.TransactionRequest;
import com.pismo.core.transaction.domain.entity.Transaction;
import com.pismo.core.transaction.domain.enums.OperationType;
import com.pismo.core.transaction.domain.strategy.OperationStrategy;
import com.pismo.core.account.domain.exception.AccountNotFoundException;
import com.pismo.core.transaction.domain.exception.InvalidOperationTypeException;
import com.pismo.core.account.service.AccountService;
import com.pismo.core.transaction.service.TransactionService;
import com.pismo.core.transaction.usecase.TransactionUsecase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Stream;

import static com.pismo.core.Stubs.ACCOUNT_ID;
import static com.pismo.core.Stubs.AMOUNT;
import static com.pismo.core.Stubs.INVALID_OPERATION_TYPE_ID;
import static com.pismo.core.Stubs.PURCHASE_OPERATION_TYPE_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionUsecaseTest {
    @Mock
    private TransactionService transactionService;

    @Mock
    private AccountService accountService;

    @Mock
    private Set<OperationStrategy> operationStrategies;

    @InjectMocks
    private TransactionUsecase transactionUsecase;

    @ParameterizedTest(name = "[{index}] should process transaction with operationType={0} and expected amount={2}")
    @CsvSource(value = {
            "1, 100, -100",
            "2, 100, -100",
            "3, 100, -100",
            "4, 100, 100"
    })
    void shouldProcessTransactionWithCorrectSignal(Integer operationTypeId,
                                                   double inputAmount,
                                                   double expectedAmount) {
        TransactionRequest request = TransactionRequest.builder()
                .accountId(ACCOUNT_ID)
                .operationTypeId(operationTypeId)
                .amount(BigDecimal.valueOf(inputAmount))
                .build();

        OperationType operationType = OperationType.fromId(operationTypeId);
        OperationStrategy operationStrategy = mock(OperationStrategy.class);

        when(operationStrategy.getOperationType())
                .thenReturn(operationType);

        when(operationStrategy.calculate(BigDecimal.valueOf(inputAmount)))
                .thenReturn(BigDecimal.valueOf(expectedAmount));

        when(operationStrategies.stream())
                .thenReturn(Stream.of(operationStrategy));

        when(accountService.accountExistsById(ACCOUNT_ID))
                .thenReturn(true);

        when(transactionService.save(any(Transaction.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Transaction result = transactionUsecase.processTransaction(request);

        assertEquals(BigDecimal.valueOf(expectedAmount), result.getAmount());
        assertEquals(ACCOUNT_ID, result.getAccountId());
        assertEquals(operationTypeId, result.getOperationTypeId());

        verify(accountService, times(1)).accountExistsById(ACCOUNT_ID);
        verify(transactionService, times(1)).save(any(Transaction.class));
    }

    @Test
    @DisplayName("Should throw exception when account is not found")
    void shouldThrowExceptionWhenAccountNotFound() {
        TransactionRequest request = TransactionRequest.builder()
                .accountId(ACCOUNT_ID)
                .operationTypeId(PURCHASE_OPERATION_TYPE_ID)
                .amount(AMOUNT)
                .build();

        when(accountService.accountExistsById(ACCOUNT_ID)).thenReturn(false);

        assertThrows(AccountNotFoundException.class, () -> transactionUsecase.processTransaction(request));
    }

    @Test
    @DisplayName("Should throw exception when operation type is invalid")
    void shouldThrowExceptionWhenOperationTypeInvalid() {
        TransactionRequest request = TransactionRequest.builder()
                .accountId(ACCOUNT_ID)
                .operationTypeId(INVALID_OPERATION_TYPE_ID)
                .amount(AMOUNT)
                .build();

        when(accountService.accountExistsById(ACCOUNT_ID)).thenReturn(true);

        assertThrows(InvalidOperationTypeException.class, () -> transactionUsecase.processTransaction(request));
    }
}
