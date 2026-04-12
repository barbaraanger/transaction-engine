package com.pismo.core.account.domain.exception;

import com.pismo.core.shared.domain.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class AccountNotFoundException extends BusinessException {
    public AccountNotFoundException(final Long id) {
        super("Account not found for id %s".formatted(id), HttpStatus.NOT_FOUND);
    }
}
