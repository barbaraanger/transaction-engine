package com.pismo.core.domain.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class BusinessException extends RuntimeException {
    private final HttpStatus status;

    public BusinessException(final String message, final HttpStatus status) {
        super(message);
        this.status = status;
    }

}
