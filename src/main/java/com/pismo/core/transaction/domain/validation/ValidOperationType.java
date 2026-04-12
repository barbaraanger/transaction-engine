package com.pismo.core.transaction.domain.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = OperationTypeValidator.class)
@Documented
public @interface ValidOperationType {
    String message() default "Invalid operation type ID. Must be a valid OperationType";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

