package com.pismo.core.transaction.domain.strategy;

import com.pismo.core.transaction.domain.enums.OperationType;
import com.pismo.core.transaction.domain.strategy.InstallmentPurchaseStrategy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("InstallmentPurchaseStrategy Tests")
class InstallmentPurchaseStrategyTest {
    private final InstallmentPurchaseStrategy installmentPurchaseStrategy = new InstallmentPurchaseStrategy();

    @Test
    @DisplayName("Should return negative amount for positive input")
    void shouldReturnNegativeAmountForPositiveInput() {
        BigDecimal input = BigDecimal.TEN;
        BigDecimal result = installmentPurchaseStrategy.calculate(input);

        assertEquals(BigDecimal.TEN.negate(), result);
    }

    @Test
    @DisplayName("Should return correct OperationType")
    void shouldReturnCorrectOperationType() {
        OperationType operationType = installmentPurchaseStrategy.getOperationType();

        assertNotNull(operationType);
        assertEquals(OperationType.INSTALLMENT_PURCHASE, operationType);
    }
}

