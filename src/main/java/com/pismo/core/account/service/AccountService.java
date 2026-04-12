package com.pismo.core.account.service;

import com.pismo.core.account.domain.entity.Account;
import com.pismo.core.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    public Account save(final Account account) {
        return accountRepository.save(account);
    }

    public boolean existsAccountByDocumentNumber(final String documentNumber) {
        return accountRepository.findAccountByDocumentNumber(documentNumber).isPresent();
    }

    public Optional<Account> findById(final Long accountId) {
        return accountRepository.findById(accountId);
    }

    public boolean accountExistsById(final Long accountId) {
        return accountRepository.existsById(accountId);
    }
}
