package com.codingshuttle.uber.uberApp.dto;

import com.codingshuttle.uber.uberApp.entities.Driver;
import com.codingshuttle.uber.uberApp.entities.enums.PaymentMethod;
import com.codingshuttle.uber.uberApp.entities.enums.RideStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RideDto {

    private Long id;
    private PointDto pickupLocation;
    private PointDto dropOffLocation;
    private LocalDateTime createdTime;
    private RiderDto rider;
    private Driver driver;
    private PaymentMethod paymentMethod;
    private RideStatus rideStatus;

    private String otp;
    private double fare;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
}
