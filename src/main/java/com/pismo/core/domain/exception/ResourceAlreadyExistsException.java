package com.pismo.core.domain.exception;

import org.springframework.http.HttpStatus;

public class ResourceAlreadyExistsException extends BusinessException {
    public ResourceAlreadyExistsException(final String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
