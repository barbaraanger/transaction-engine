package com.pismo.core.controller;

import com.pismo.core.controller.docs.AccountControllerDocumentation;
import com.pismo.core.domain.dto.request.CreateAccountRequest;
import com.pismo.core.domain.dto.response.AccountResponse;
import com.pismo.core.domain.entity.Account;
import com.pismo.core.domain.mapper.AccountMapper;
import com.pismo.core.usecase.AccountUsecase;
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
