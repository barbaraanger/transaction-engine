package com.pismo.core.account.usecase;

import com.pismo.core.account.domain.dto.request.CreateAccountRequest;
import com.pismo.core.account.domain.entity.Account;
import com.pismo.core.account.domain.exception.AccountNotFoundException;
import com.pismo.core.account.domain.exception.ResourceAlreadyExistsException;
import com.pismo.core.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountUsecase {
    private final AccountService accountService;

    public Account createAccount(final CreateAccountRequest createAccountRequest) {
        if (accountService.existsAccountByDocumentNumber(createAccountRequest.documentNumber())) {
            throw new ResourceAlreadyExistsException("Account with document number already exists");
        }

        final Account account = Account.builder()
                .documentNumber(createAccountRequest.documentNumber())
                .build();
        return accountService.save(account);
    }

    public Account getAccountById(final Long accountId) {
        return accountService.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));
    }
}
