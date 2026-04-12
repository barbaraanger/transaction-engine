package com.pismo.core.domain.enums;

import com.pismo.core.domain.exception.InvalidOperationTypeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class OperationTypeTest {

    @ParameterizedTest(name = "Should return {1} when id is {0}")
    @CsvSource({
            "1, PURCHASE",
            "2, INSTALLMENT_PURCHASE",
            "3, WITHDRAWAL",
            "4, PAYMENT"
    })
    void shouldReturnCorrectOperationType(int id, OperationType expected) {
        OperationType result = OperationType.fromId(id);

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Should throw InvalidTransactionException when id is invalid")
    void shouldThrowExceptionWhenIdIsInvalid() {
        int invalidId = 99;

        InvalidOperationTypeException exception = assertThrows(
                InvalidOperationTypeException.class,
                () -> OperationType.fromId(invalidId)
        );

        assertTrue(exception.getMessage().contains(String.valueOf(invalidId)));
    }
}