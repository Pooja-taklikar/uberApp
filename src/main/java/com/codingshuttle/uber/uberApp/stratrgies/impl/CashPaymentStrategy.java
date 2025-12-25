package com.codingshuttle.uber.uberApp.stratrgies.impl;

import com.codingshuttle.uber.uberApp.entities.Driver;
import com.codingshuttle.uber.uberApp.entities.Payment;
import com.codingshuttle.uber.uberApp.entities.Wallet;
import com.codingshuttle.uber.uberApp.entities.enums.PaymentStatus;
import com.codingshuttle.uber.uberApp.entities.enums.TransactionMethod;
import com.codingshuttle.uber.uberApp.repositories.PaymentRepository;
import com.codingshuttle.uber.uberApp.services.PaymentService;
import com.codingshuttle.uber.uberApp.services.WalletService;
import com.codingshuttle.uber.uberApp.stratrgies.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CashPaymentStrategy implements PaymentStrategy {

    private  final WalletService walletService;
    private final PaymentRepository paymentRepository;

    @Override
    public void processPayment(Payment payment) {
        Driver driver = payment.getRide().getDriver();

        double platformCommision = payment.getAmount() * PLATFORM_COMMISSION;
        walletService.deductMoneyFromWallet(driver.getUser(),platformCommision,null,
                payment.getRide(), TransactionMethod.RIDE);

        payment.setPaymentStatus(PaymentStatus.CONFIRMED);
        paymentRepository.save(payment);
    }
}
