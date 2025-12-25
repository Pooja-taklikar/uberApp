package com.codingshuttle.uber.uberApp.services;

import com.codingshuttle.uber.uberApp.dto.WalletTransactionDto;
import com.codingshuttle.uber.uberApp.entities.WalletTransaction;

public interface WalletTransactionService {
     void createNewWalletTransaction(WalletTransaction walletTransaction);
}
