package com.pismo.core.account.service;

import com.pismo.core.account.domain.entity.Account;
import com.pismo.core.account.repository.AccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.pismo.core.Stubs.ACCOUNT_ID;
import static com.pismo.core.Stubs.DOCUMENT_NUMBER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @Test
    @DisplayName("Should save account successfully")
    void shouldSaveAccount() {
        Account account = new Account(ACCOUNT_ID, DOCUMENT_NUMBER);

        when(accountRepository.save(account)).thenReturn(account);

        Account result = accountService.save(account);

        assertEquals(account, result);
        verify(accountRepository).save(account);
    }

    @Test
    @DisplayName("Should return true when account exists by document number")
    void shouldReturnTrueWhenAccountExistsByDocumentNumber() {
        when(accountRepository.findAccountByDocumentNumber(DOCUMENT_NUMBER))
                .thenReturn(Optional.of(new Account()));

        boolean result = accountService.existsAccountByDocumentNumber(DOCUMENT_NUMBER);

        assertTrue(result);
        verify(accountRepository).findAccountByDocumentNumber(DOCUMENT_NUMBER);
    }

    @Test
    @DisplayName("Should return false when account does not exist by document number")
    void shouldReturnFalseWhenAccountDoesNotExistByDocumentNumber() {
        when(accountRepository.findAccountByDocumentNumber(DOCUMENT_NUMBER))
                .thenReturn(Optional.empty());

        boolean result = accountService.existsAccountByDocumentNumber(DOCUMENT_NUMBER);

        assertFalse(result);
        verify(accountRepository).findAccountByDocumentNumber(DOCUMENT_NUMBER);
    }

    @Test
    @DisplayName("Should return account when found by id")
    void shouldReturnAccountWhenFoundById() {
        Account account = new Account(ACCOUNT_ID, DOCUMENT_NUMBER);

        when(accountRepository.findById(ACCOUNT_ID))
                .thenReturn(Optional.of(account));

        Optional<Account> result = accountService.findById(ACCOUNT_ID);

        assertTrue(result.isPresent());
        assertEquals(account, result.get());
        verify(accountRepository).findById(ACCOUNT_ID);
    }

    @Test
    @DisplayName("Should return empty when account not found by id")
    void shouldReturnEmptyWhenAccountNotFoundById() {
        when(accountRepository.findById(ACCOUNT_ID))
                .thenReturn(Optional.empty());

        Optional<Account> result = accountService.findById(ACCOUNT_ID);

        assertTrue(result.isEmpty());
        verify(accountRepository).findById(ACCOUNT_ID);
    }

    @Test
    @DisplayName("Should return true when account exists by id")
    void shouldReturnTrueWhenAccountExistsById() {
        when(accountRepository.existsById(ACCOUNT_ID)).thenReturn(true);

        boolean result = accountService.accountExistsById(ACCOUNT_ID);

        assertTrue(result);
        verify(accountRepository).existsById(ACCOUNT_ID);
    }

    @Test
    @DisplayName("Should return false when account does not exist by id")
    void shouldReturnFalseWhenAccountDoesNotExistById() {
        when(accountRepository.existsById(ACCOUNT_ID)).thenReturn(false);

        boolean result = accountService.accountExistsById(ACCOUNT_ID);

        assertFalse(result);
        verify(accountRepository).existsById(ACCOUNT_ID);
    }
}
