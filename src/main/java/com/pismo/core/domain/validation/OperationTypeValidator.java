package com.pismo.core.domain.validation;

import com.pismo.core.domain.enums.OperationType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class OperationTypeValidator implements ConstraintValidator<ValidOperationType, Integer> {
    @Override
    public boolean isValid(final Integer value, final ConstraintValidatorContext context) {
        if (Objects.isNull(value)) {
            return true;
        }

        OperationType.fromId(value);
        return true;
    }
}

