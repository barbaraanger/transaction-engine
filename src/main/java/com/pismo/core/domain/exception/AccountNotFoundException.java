package com.pismo.core.domain.exception;

import org.springframework.http.HttpStatus;

public class AccountNotFoundException extends BusinessException {
    public AccountNotFoundException(final Long id) {
        super("Account not found for id %s".formatted(id), HttpStatus.NOT_FOUND);
    }
}
