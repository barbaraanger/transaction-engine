package com.pismo.core.account.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Accounts")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Account_ID")
    private Long accountId;

    @Column(name = "Document_Number", length = 20, unique = true, nullable = false)
    private String documentNumber;

}
