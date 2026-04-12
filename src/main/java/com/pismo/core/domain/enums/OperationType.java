package com.pismo.core.domain.enums;

import com.pismo.core.domain.exception.InvalidOperationTypeException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum OperationType {
    PURCHASE(1),
    INSTALLMENT_PURCHASE(2),
    WITHDRAWAL(3),
    PAYMENT(4);

    private final int id;

    public static OperationType fromId(int id) {
        return Arrays.stream(values())
                .filter(operationType -> operationType.id == id)
                .findFirst()
                .orElseThrow(() -> new InvalidOperationTypeException(id));
    }
}
