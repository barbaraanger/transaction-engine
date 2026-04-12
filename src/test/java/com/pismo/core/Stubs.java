package com.pismo.core;

import java.math.BigDecimal;

public class Stubs {
    public static final String ACCOUNT_NOT_FOUND_ERROR = "Account not found for id %s";
    public static final String RESOURCE_ALREADY_EXISTS_ERROR = "Account with document number %s already exists";
    public static final String DOCUMENT_NUMBER_DOCUMENT_NUMBER_IS_REQUIRED_ERROR = "documentNumber - Document number is required";
    public static final String OPERATION_TYPE_INVALID_ERROR = "operationTypeId - Invalid operation type ID: 99";
    public static final String AMOUNT_MUST_BE_POSITIVE_ERROR = "amount - Amount must be positive";
    public static final String AMOUNT_IS_REQUIRED_ERROR = "amount - Amount is required";
    public static final String OPERATION_TYPE_ID_IS_REQUIRED_ERROR = "operationTypeId - Operation Type ID is required";
    public static final String ACCOUNT_ID_IS_REQUIRED_ERROR = "accountId - Account ID is required";
    public static final String AMOUNT_MUST_BE_A_VALID_MONETARY_VALUE_ERROR = "amount - Amount must be a valid monetary value with up to 10 digits and 2 decimal places";

    public static final Long ACCOUNT_ID = 1L;
    public static final Long TRANSACTION_ID = 1L;
    public static final Integer OPERATION_TYPE_ID = 1;
    public static final String DOCUMENT_NUMBER = "123456789010";
    public static final BigDecimal AMOUNT = BigDecimal.valueOf(100.0);

    public static final Integer PURCHASE_OPERATION_TYPE_ID = 1;
    public static final Integer PAYMENT_OPERATION_TYPE_ID = 4;
    public static final Integer INVALID_OPERATION_TYPE_ID = 99;
}
