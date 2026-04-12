package com.pismo.core.account.usecase;

import com.pismo.core.account.domain.dto.request.CreateAccountRequest;
import com.pismo.core.account.domain.entity.Account;
import com.pismo.core.account.domain.exception.AccountNotFoundException;
import com.pismo.core.account.domain.exception.ResourceAlreadyExistsException;
import com.pismo.core.account.service.AccountService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.pismo.core.Stubs.DOCUMENT_NUMBER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountUsecaseTest {
    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountUsecase accountUsecase;

    @Test
    @DisplayName("Should create account when account with document number does not exist")
    void shouldCreateAccountWhenNoAccountWithThatDocumentNumber() {
        CreateAccountRequest createAccountRequest = CreateAccountRequest.builder()
                .documentNumber(DOCUMENT_NUMBER)
                .build();
        when(accountService.existsAccountByDocumentNumber(createAccountRequest.documentNumber()))
                .thenReturn(false);

        when(accountService.save(any(Account.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Account accountResponse = accountUsecase.createAccount(createAccountRequest);

        assertEquals(DOCUMENT_NUMBER, accountResponse.getDocumentNumber());
        verify(accountService, times(1)).save(any(Account.class));
    }

    @Test
    @DisplayName("Should throw exception when creating account with existing document number")
    void shouldThrowExceptionWhenAccountAlreadyExists() {
        CreateAccountRequest createAccountRequest = CreateAccountRequest.builder()
                .documentNumber(DOCUMENT_NUMBER)
                .build();
        when(accountService.existsAccountByDocumentNumber(createAccountRequest.documentNumber()))
                .thenReturn(true);

        assertThrows(ResourceAlreadyExistsException.class, () -> accountUsecase.createAccount(createAccountRequest));
    }

    @Test
    @DisplayName("Should return account when account exists by id")
    void shouldReturnAccountWhenAccountExistsByIdById() {
        Account account = Account.builder()
                .accountId(1L)
                .documentNumber(DOCUMENT_NUMBER)
                .build();

        when(accountService.findById(1L))
                .thenReturn(Optional.of(account));

        Account result = accountUsecase.getAccountById(1L);

        assertEquals(1L, result.getAccountId());
    }

    @Test
    @DisplayName("Should throw exception when account is not found by id")
    void shouldThrowExceptionWhenAccountNotFoundById() {
        when(accountService.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class,
                () -> accountUsecase.getAccountById(1L));
    }
}
