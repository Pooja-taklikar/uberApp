package com.codingshuttle.uber.uberApp.stratrgies.impl;

import com.codingshuttle.uber.uberApp.entities.Driver;
import com.codingshuttle.uber.uberApp.entities.Payment;
import com.codingshuttle.uber.uberApp.entities.Rider;
import com.codingshuttle.uber.uberApp.entities.enums.PaymentStatus;
import com.codingshuttle.uber.uberApp.entities.enums.TransactionMethod;
import com.codingshuttle.uber.uberApp.repositories.PaymentRepository;
import com.codingshuttle.uber.uberApp.services.PaymentService;
import com.codingshuttle.uber.uberApp.services.WalletService;
import com.codingshuttle.uber.uberApp.stratrgies.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//Rider has 232 rupes in wallet, drived had 500
//Ride Cost 100, commision = 30
//Rider -> 232 - 100
//Driver -> 500 + (100 - 30) = 70
@Service
@RequiredArgsConstructor
public class WalletPaymentStrategy implements PaymentStrategy {

    private final WalletService walletService;
    private final PaymentRepository paymentRepository;

    @Override
    @Transactional
    public void processPayment(Payment payment) {
        Driver driver = payment.getRide().getDriver();
        Rider rider = payment.getRide().getRider();

        walletService.deductMoneyFromWallet(rider.getUser(), payment.getAmount(),
                null,payment.getRide(), TransactionMethod.RIDE);
        double driversCut = payment.getAmount() * (1 - PLATFORM_COMMISSION);

        walletService.addMoneyToWallet(driver.getUser(), driversCut,null,payment.getRide(),TransactionMethod.RIDE);

        payment.setPaymentStatus(PaymentStatus.CONFIRMED);
        paymentRepository.save(payment);
    }
}
