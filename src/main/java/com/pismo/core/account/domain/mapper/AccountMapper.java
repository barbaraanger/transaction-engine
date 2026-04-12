package com.pismo.core.account.domain.mapper;

import com.pismo.core.account.domain.dto.response.AccountResponse;
import com.pismo.core.account.domain.entity.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {
    public static AccountResponse toResponse(final Account account) {
        return AccountResponse.builder()
                .accountId(account.getAccountId())
                .documentNumber(account.getDocumentNumber())
                .build();
    }
}
