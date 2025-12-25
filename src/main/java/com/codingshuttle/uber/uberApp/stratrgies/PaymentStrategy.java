package com.codingshuttle.uber.uberApp.stratrgies;

import com.codingshuttle.uber.uberApp.entities.Payment;

public interface PaymentStrategy {

    Double PLATFORM_COMMISSION = 0.3;
    void processPayment(Payment payment);
}
