package com.codingshuttle.uber.uberApp.services;

import com.codingshuttle.uber.uberApp.entities.RideRequest;

public interface RideRequestService {

    RideRequest findRideRequestById(Long rideRequestId);

    void update(RideRequest rideRequest);
}
