package com.codingshuttle.uber.uberApp.services.Impl;

import com.codingshuttle.uber.uberApp.dto.DriverDto;
import com.codingshuttle.uber.uberApp.dto.RideDto;
import com.codingshuttle.uber.uberApp.dto.RideRequestDto;
import com.codingshuttle.uber.uberApp.dto.RiderDto;
import com.codingshuttle.uber.uberApp.entities.*;
import com.codingshuttle.uber.uberApp.entities.enums.RideRequestStatus;
import com.codingshuttle.uber.uberApp.entities.enums.RideStatus;
import com.codingshuttle.uber.uberApp.exceptions.ResourceNotFoundException;
import com.codingshuttle.uber.uberApp.repositories.RideRequestRepository;
import com.codingshuttle.uber.uberApp.repositories.RiderRepository;
import com.codingshuttle.uber.uberApp.services.DriverService;
import com.codingshuttle.uber.uberApp.services.RatingService;
import com.codingshuttle.uber.uberApp.services.RideService;
import com.codingshuttle.uber.uberApp.services.RiderService;
import com.codingshuttle.uber.uberApp.stratrgies.RideStrategyManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RiderServiceImpl implements RiderService {

    private final ModelMapper modelMapper;
    private final RideStrategyManager strategyManager;
    private final RideRequestRepository rideRequestRepository;
    private final RiderRepository riderRepository;
    private final RideService rideService;
    private final DriverService driverService;
    private final RatingService ratingService;

    @Override
    @Transactional
    public RideRequestDto requestRide(RideRequestDto rideRequestDto) {
        Rider rider = getCurrentRider();
        RideRequest rideRequest = modelMapper.map(rideRequestDto,RideRequest.class);

        log.info(rideRequest.toString());
        rideRequest.setRideRequestStatus(RideRequestStatus.PENDING);
        rideRequest.setRider(rider);

        Double fare = strategyManager.rideFareCalculationStrategy().calculateFare(rideRequest);
        rideRequest.setFare(fare);

        RideRequest saveRideRequest = rideRequestRepository.save(rideRequest);

        List<Driver> drivers = strategyManager.driverMatchingStrategy(rider.getRating()).findMatchingDriver(rideRequest);

//      Todo send notifaction to all the drivers
        return modelMapper.map(saveRideRequest,RideRequestDto.class);
    }

    @Override
    public RideDto cancelRide(Long rideId) {
        Rider rider = getCurrentRider();
        Ride ride = rideService.getRideById(rideId);

        if(!rider.equals(ride.getRider())){
            throw new RuntimeException("Rider does not own this ride with id: "+ rideId);
        }

        if(!ride.getRideStatus().equals(RideStatus.CONFIRMED))
        {
            throw new RuntimeException("Ride can not be cancelled, invalid satus" + ride.getRideStatus());
        }

        Ride savedRide = rideService.updateRideStatus(ride,RideStatus.CANCELLED);
        driverService.updateDriverAvailability(ride.getDriver(),true);

        return modelMapper.map(savedRide,RideDto.class);
    }

    @Override
    public DriverDto rateDriver(Long rideId, Integer rating) {
        Ride ride = rideService.getRideById(rideId);
        Rider rider = getCurrentRider();

        if(!rider.equals(ride.getRider())){
            throw new RuntimeException("Rider is not owner of this ride");
        }

        if(!ride.getRideStatus().equals(RideStatus.ENDED))
        {
            throw new RuntimeException("Ride status is not ENDED hence cannot be start rating " + ride.getRideStatus());
        }
        return ratingService.rateDriver(ride,rating);
    }

    @Override
    public RiderDto getMyProfile() {
        Rider currentRider = getCurrentRider();
        return modelMapper.map(currentRider,RiderDto.class);
    }

    @Override
    public Page<RideDto> getAllMyRides(PageRequest pageRequest) {
        Rider currentRider = getCurrentRider();
        return rideService.getAllRidesOfRider(currentRider, pageRequest)
                .map(ride -> modelMapper.map(ride,RideDto.class));
    }

    @Override
    public Rider createNewRider(User user) {
        Rider rider = Rider.builder()
                .user(user)
                .rating(0.0)
                .build();
        return riderRepository.save(rider);
    }

    @Override
    public Rider getCurrentRider() {
        // Implement using Spring Security
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return riderRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException(
                "Rider not associated with user with Id " + user.getId()
        ));
    }


}
