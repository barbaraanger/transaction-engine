package com.pismo.core.transaction.domain.validation;

import com.pismo.core.transaction.domain.exception.InvalidOperationTypeException;
import com.pismo.core.transaction.domain.validation.OperationTypeValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class OperationTypeValidatorTest {
    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    @InjectMocks
    private OperationTypeValidator operationTypeValidator;

    @ParameterizedTest(name = "Should successfully validate operation type id: {0}")
    @ValueSource(ints = {1, 2, 3, 4})
    @NullSource
    void shouldValidateAllValidOperationTypesParametrized(Integer operationTypeId) {
        boolean isValid = operationTypeValidator.isValid(operationTypeId, constraintValidatorContext);
        assertTrue(isValid);
    }

    @ParameterizedTest(name = "Should reject invalid operation type id: {0}")
    @ValueSource(ints = {0, -1, 1000})
    void shouldRejectAllInvalidOperationTypesParametrized(Integer operationTypeId) {
        assertThrows(InvalidOperationTypeException.class, () -> operationTypeValidator.isValid(operationTypeId, constraintValidatorContext));
    }

}

