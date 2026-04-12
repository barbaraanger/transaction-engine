package com.pismo.core.domain.strategy;

import com.pismo.core.domain.enums.OperationType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class WithdrawalStrategy implements OperationStrategy {
    public BigDecimal calculate(final BigDecimal amount) {
        return amount.abs().negate();
    }

    public OperationType getOperationType() {
        return OperationType.WITHDRAWAL;
    }
}
