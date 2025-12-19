package com.codingshuttle.uber.uberApp.entities;

import com.codingshuttle.uber.uberApp.entities.enums.TransactionMethod;
import com.codingshuttle.uber.uberApp.entities.enums.TransactionType;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
public class WalletTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;

    private TransactionType transactionType;

    private TransactionMethod transactionMethod;

    @OneToOne
    private Ride ride;

    private String transactionId;

    @CreationTimestamp
    private LocalDateTime timestamp;

    @ManyToOne
    private Wallet wallet;
}
