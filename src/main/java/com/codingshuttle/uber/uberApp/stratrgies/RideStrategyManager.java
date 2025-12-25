package com.codingshuttle.uber.uberApp.stratrgies;


import com.codingshuttle.uber.uberApp.stratrgies.impl.DriverMatchingHighestRatedDriver;
import com.codingshuttle.uber.uberApp.stratrgies.impl.DriverMatchingNearestDriverStrategy;
import com.codingshuttle.uber.uberApp.stratrgies.impl.RideFareDefaultFareCalculationStrategy;
import com.codingshuttle.uber.uberApp.stratrgies.impl.RideFareSurgePricingFareCalculationStrategy;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@AllArgsConstructor
public class RideStrategyManager {

    private final DriverMatchingHighestRatedDriver driverMatchingHighestRatedDriver;
    private final DriverMatchingNearestDriverStrategy driverMatchingNearestDriverStrategy;
    private final RideFareDefaultFareCalculationStrategy rideFareDefaultFareCalculationStrategy;
    private final RideFareSurgePricingFareCalculationStrategy rideFareSurgePricingFareCalculationStrategy;

    public DriverMatchingStrategy driverMatchingStrategy(double driverRating){
        if(driverRating >= 4.8){
            return driverMatchingHighestRatedDriver;
        }else{
            return driverMatchingNearestDriverStrategy;
        }
    };

    public RideFareCalculationStrategy rideFareCalculationStrategy(){

        LocalTime surgeStartTime = LocalTime.of(18,0);
        LocalTime surgeEndTime = LocalTime.of(21,0);
        LocalTime currentTime = LocalTime.now();

        boolean isSurgeTime = currentTime.isAfter(surgeEndTime) && currentTime.isAfter(surgeEndTime);

        if(isSurgeTime){
            return  rideFareSurgePricingFareCalculationStrategy;
        }else {
            return rideFareDefaultFareCalculationStrategy;
        }
    }
}
