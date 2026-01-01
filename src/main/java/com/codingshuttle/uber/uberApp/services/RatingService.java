package com.codingshuttle.uber.uberApp.services;

import com.codingshuttle.uber.uberApp.dto.DriverDto;
import com.codingshuttle.uber.uberApp.dto.RiderDto;
import com.codingshuttle.uber.uberApp.entities.Driver;
import com.codingshuttle.uber.uberApp.entities.Ride;
import com.codingshuttle.uber.uberApp.entities.Rider;

public interface RatingService {

    DriverDto rateDriver(Ride ride, Integer rating);

    RiderDto rateRider(Ride ride, Integer rating);

    void createNewRating(Ride ride);
}
