package com.codingshuttle.uber.uberApp.stratrgies;

import com.codingshuttle.uber.uberApp.entities.enums.PaymentMethod;
import com.codingshuttle.uber.uberApp.stratrgies.impl.CashPaymentStrategy;
import com.codingshuttle.uber.uberApp.stratrgies.impl.WalletPaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.codingshuttle.uber.uberApp.entities.enums.PaymentMethod.CASH;
import static com.codingshuttle.uber.uberApp.entities.enums.PaymentMethod.WALLET;

@Component
@RequiredArgsConstructor
public class PaymentStrategyManager {

    private final WalletPaymentStrategy walletPaymentStrategy;
    private final CashPaymentStrategy cashPaymentStrategy;

    public PaymentStrategy paymentStrategy(PaymentMethod paymentMethod){
        return switch (paymentMethod){
            case WALLET -> walletPaymentStrategy;
            case CASH -> cashPaymentStrategy;
            default -> throw new RuntimeException("Invalid Payment Mathod");
        };
    }

}

