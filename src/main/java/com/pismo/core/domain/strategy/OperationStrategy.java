package com.pismo.core.domain.strategy;

import com.pismo.core.domain.enums.OperationType;

import java.math.BigDecimal;

public interface OperationStrategy {
    BigDecimal calculate(BigDecimal amount);
    OperationType getOperationType();
}
