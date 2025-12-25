package com.codingshuttle.uber.uberApp.services;

import com.codingshuttle.uber.uberApp.entities.Ride;
import com.codingshuttle.uber.uberApp.entities.User;
import com.codingshuttle.uber.uberApp.entities.Wallet;
import com.codingshuttle.uber.uberApp.entities.enums.TransactionMethod;
import org.springframework.stereotype.Service;


public interface WalletService {
    Wallet addMoneyToWallet(User user, Double amount, String transactionId, Ride ride, TransactionMethod transactionMethod);

    Wallet deductMoneyFromWallet(User user, Double amount, String transactionId, Ride ride, TransactionMethod transactionMethod);

    void withdrawAllMyMoneyFromWallet();

    Wallet findWalletById(Long walletId);

    Wallet createNewWallet(User user);

    Wallet findByUser(User user);
}
