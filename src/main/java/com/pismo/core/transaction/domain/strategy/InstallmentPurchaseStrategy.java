package com.pismo.core.transaction.domain.strategy;

import com.pismo.core.transaction.domain.enums.OperationType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class InstallmentPurchaseStrategy implements OperationStrategy {
    public BigDecimal calculate(final BigDecimal amount) {
        return amount.abs().negate();
    }

    public OperationType getOperationType() {
        return OperationType.INSTALLMENT_PURCHASE;
    }
}
