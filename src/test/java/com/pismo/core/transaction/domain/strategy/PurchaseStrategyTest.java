package com.pismo.core.transaction.domain.strategy;

import com.pismo.core.transaction.domain.enums.OperationType;
import com.pismo.core.transaction.domain.strategy.PurchaseStrategy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("PurchaseStrategy Tests")
class PurchaseStrategyTest {
    private final PurchaseStrategy purchaseStrategy = new PurchaseStrategy();

    @Test
    @DisplayName("Should return negative amount for positive input")
    void shouldReturnNegativeAmountForPositiveInput() {
        BigDecimal input = BigDecimal.TEN;
        BigDecimal result = purchaseStrategy.calculate(input);

        assertEquals(BigDecimal.TEN.negate(), result);
    }

    @Test
    @DisplayName("Should return correct OperationType")
    void shouldReturnCorrectOperationType() {
        OperationType operationType = purchaseStrategy.getOperationType();

        assertNotNull(operationType);
        assertEquals(OperationType.PURCHASE, operationType);
    }
}

