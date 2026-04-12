package com.pismo.core.transaction.domain.exception;

import com.pismo.core.shared.domain.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class InvalidOperationTypeException extends BusinessException {
    public InvalidOperationTypeException(final Integer operationTypeId) {
        super("operationTypeId - Invalid operation type ID: %s".formatted(operationTypeId), HttpStatus.UNPROCESSABLE_CONTENT);
    }
}
