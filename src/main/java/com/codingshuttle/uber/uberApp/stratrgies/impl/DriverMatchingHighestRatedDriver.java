package com.codingshuttle.uber.uberApp.stratrgies.impl;

import com.codingshuttle.uber.uberApp.entities.Driver;
import com.codingshuttle.uber.uberApp.entities.RideRequest;
import com.codingshuttle.uber.uberApp.repositories.DriverRepository;
import com.codingshuttle.uber.uberApp.stratrgies.DriverMatchingStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverMatchingHighestRatedDriver implements DriverMatchingStrategy {

    private final DriverRepository driverRepository;

    @Override
    public List<Driver> findMatchingDriver(RideRequest rideRequest) {

        return driverRepository.findTenNearByTopRatedDrivers(rideRequest.getPickupLocation());
    }
}
