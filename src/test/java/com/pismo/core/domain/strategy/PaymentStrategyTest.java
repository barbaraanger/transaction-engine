package com.pismo.core.domain.strategy;

import com.pismo.core.domain.enums.OperationType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("PaymentStrategy Tests")
class PaymentStrategyTest {
    private final PaymentStrategy paymentStrategy = new PaymentStrategy();

    @Test
    @DisplayName("Should return positive amount for positive input")
    void shouldReturnPositiveAmountForPositiveInput() {
        BigDecimal input = BigDecimal.TEN;
        BigDecimal result = paymentStrategy.calculate(input);

        assertEquals(BigDecimal.TEN, result);
    }

    @Test
    @DisplayName("Should return correct OperationType")
    void shouldReturnCorrectOperationType() {
        OperationType operationType = paymentStrategy.getOperationType();

        assertNotNull(operationType);
        assertEquals(OperationType.PAYMENT, operationType);
    }
}
