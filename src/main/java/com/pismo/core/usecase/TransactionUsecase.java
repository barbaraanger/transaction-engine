package com.pismo.core.usecase;

import com.pismo.core.domain.dto.request.TransactionRequest;
import com.pismo.core.domain.entity.Transaction;
import com.pismo.core.domain.strategy.OperationStrategy;
import com.pismo.core.exception.AccountNotFoundException;
import com.pismo.core.exception.InvalidOperationTypeException;
import com.pismo.core.service.AccountService;
import com.pismo.core.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TransactionUsecase {
    private final AccountService accountService;
    private final Set<OperationStrategy> operationStrategies;
    private final TransactionService transactionService;

    public Transaction processTransaction(final TransactionRequest transactionRequest) {
        final Long accountId = transactionRequest.accountId();
        final Integer operationTypeId = transactionRequest.operationTypeId();

        validateAccount(accountId);

        final OperationStrategy operationStrategy = findOperationStrategy(operationTypeId);

        final Transaction transaction = Transaction.builder()
                .accountId(accountId)
                .operationTypeId(operationTypeId)
                .amount(calculateAmount(transactionRequest, operationStrategy))
                .eventDate(LocalDateTime.now())
                .build();

        return transactionService.save(transaction);
    }

    private void validateAccount(final Long accountId) {
        if (!accountService.accountExistsById(accountId)) {
           throw new AccountNotFoundException(accountId);
        }
    }

    private @NonNull OperationStrategy findOperationStrategy(final Integer operationTypeId) {
        return operationStrategies.stream()
                .filter(calculator ->
                        calculator.getOperationType().getId() == operationTypeId)
                .findFirst()
                .orElseThrow(() -> new InvalidOperationTypeException(operationTypeId));
    }

    private static BigDecimal calculateAmount(final TransactionRequest transactionRequest, final OperationStrategy operationStrategy) {
        return operationStrategy.calculate(transactionRequest.amount());
    }
}
