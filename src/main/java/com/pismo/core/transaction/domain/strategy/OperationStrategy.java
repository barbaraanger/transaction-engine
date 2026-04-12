package com.pismo.core.transaction.domain.strategy;

import com.pismo.core.transaction.domain.enums.OperationType;

import java.math.BigDecimal;

public interface OperationStrategy {
    BigDecimal calculate(BigDecimal amount);
    OperationType getOperationType();
}
