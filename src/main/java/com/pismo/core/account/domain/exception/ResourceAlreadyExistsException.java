package com.pismo.core.account.domain.exception;

import com.pismo.core.shared.domain.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class ResourceAlreadyExistsException extends BusinessException {
    public ResourceAlreadyExistsException(final String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
