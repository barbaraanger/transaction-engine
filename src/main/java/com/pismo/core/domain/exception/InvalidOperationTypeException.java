package com.pismo.core.domain.exception;

import org.springframework.http.HttpStatus;

public class InvalidOperationTypeException extends BusinessException {
    public InvalidOperationTypeException(final Integer operationTypeId) {
        super("operationTypeId - Invalid operation type ID: %s".formatted(operationTypeId), HttpStatus.UNPROCESSABLE_CONTENT);
    }
}
