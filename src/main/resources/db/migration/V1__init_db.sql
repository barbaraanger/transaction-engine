CREATE TABLE Accounts
(
    Account_ID BIGSERIAL PRIMARY KEY,
    Document_Number VARCHAR(20) NOT NULL
);

CREATE TABLE OperationTypes
(
    OperationType_ID BIGINT PRIMARY KEY,
    Description VARCHAR(255) NOT NULL
);

CREATE TABLE Transactions
(
    Transaction_ID    BIGSERIAL PRIMARY KEY,
    Account_ID        BIGSERIAL NOT NULL REFERENCES Accounts (Account_ID),
    OperationType_ID BIGINT NOT NULL REFERENCES OperationTypes (OperationType_ID),
    Amount DECIMAL(10,2) NOT NULL,
    EventDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO OperationTypes (OperationType_ID, Description) VALUES (1, 'PURCHASE');
INSERT INTO OperationTypes (OperationType_ID, Description) VALUES (2, 'INSTALLMENT_PURCHASE');
INSERT INTO OperationTypes (OperationType_ID, Description) VALUES (3, 'WITHDRAWAL');
INSERT INTO OperationTypes (OperationType_ID, Description) VALUES (4, 'PAYMENT');
