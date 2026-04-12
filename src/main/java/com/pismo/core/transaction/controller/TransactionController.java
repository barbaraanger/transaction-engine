package com.pismo.core.transaction.controller;

import com.pismo.core.transaction.controller.docs.TransactionControllerDocumentation;
import com.pismo.core.transaction.domain.dto.request.TransactionRequest;
import com.pismo.core.transaction.domain.dto.response.TransactionResponse;
import com.pismo.core.transaction.domain.entity.Transaction;
import com.pismo.core.transaction.domain.mapper.TransactionMapper;
import com.pismo.core.transaction.usecase.TransactionUsecase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController implements TransactionControllerDocumentation {
    private final TransactionUsecase transactionUsecase;

    @PostMapping
    public ResponseEntity<TransactionResponse> create(@RequestBody @Valid final TransactionRequest request) {
        Transaction transaction = transactionUsecase.processTransaction(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(TransactionMapper.toResponse(transaction));
    }
}
