package com.codingshuttle.uber.uberApp.dto;

import com.codingshuttle.uber.uberApp.entities.Ride;
import com.codingshuttle.uber.uberApp.entities.Wallet;
import com.codingshuttle.uber.uberApp.entities.enums.TransactionMethod;
import com.codingshuttle.uber.uberApp.entities.enums.TransactionType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Builder
public class WalletTransactionDto {
    private Long id;

    private Double amount;

    private TransactionType transactionType;

    private TransactionMethod transactionMethod;

    private RideDto rideDto;

    private String transactionId;

    private LocalDateTime timestamp;

    private WalletDto walletDto;
}
