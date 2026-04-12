package com.pismo.core.account.controller;

import com.pismo.core.account.controller.docs.AccountControllerDocumentation;
import com.pismo.core.account.domain.dto.request.CreateAccountRequest;
import com.pismo.core.account.domain.dto.response.AccountResponse;
import com.pismo.core.account.domain.entity.Account;
import com.pismo.core.account.domain.mapper.AccountMapper;
import com.pismo.core.account.usecase.AccountUsecase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController implements AccountControllerDocumentation {
    private final AccountUsecase accountUsecase;

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@RequestBody @Valid final CreateAccountRequest createAccountRequest) {
        Account account = accountUsecase.createAccount(createAccountRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(AccountMapper.toResponse(account));
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable final Long accountId) {
        Account account = accountUsecase.getAccountById(accountId);
        return ResponseEntity.ok(AccountMapper.toResponse(account));
    }
}
