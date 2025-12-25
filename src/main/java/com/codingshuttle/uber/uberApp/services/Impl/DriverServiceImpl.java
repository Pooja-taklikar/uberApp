package com.codingshuttle.uber.uberApp.services.Impl;

import com.codingshuttle.uber.uberApp.dto.DriverDto;
import com.codingshuttle.uber.uberApp.dto.RideDto;
import com.codingshuttle.uber.uberApp.dto.RiderDto;
import com.codingshuttle.uber.uberApp.entities.Driver;
import com.codingshuttle.uber.uberApp.entities.Ride;
import com.codingshuttle.uber.uberApp.entities.RideRequest;
import com.codingshuttle.uber.uberApp.entities.enums.RideRequestStatus;
import com.codingshuttle.uber.uberApp.entities.enums.RideStatus;
import com.codingshuttle.uber.uberApp.exceptions.ResourceNotFoundException;
import com.codingshuttle.uber.uberApp.repositories.DriverRepository;
import com.codingshuttle.uber.uberApp.services.DriverService;
import com.codingshuttle.uber.uberApp.services.PaymentService;
import com.codingshuttle.uber.uberApp.services.RideRequestService;
import com.codingshuttle.uber.uberApp.services.RideService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final RideRequestService rideRequestService;
    private final DriverRepository driverRepository;
    private final RideService rideService;
    private final ModelMapper modelMapper;
    private final PaymentService paymentService;

    @Override
    @Transactional
    public RideDto acceptRide(Long rideRequestId) {
        RideRequest rideRequest = rideRequestService.findRideRequestById(rideRequestId);

        if(!rideRequest.getRideRequestStatus().equals(RideRequestStatus.PENDING)){
            throw new RuntimeException("RideRequest cannot be accepted, status is " + rideRequest.getRideRequestStatus());
        }

        Driver currentDriver = getCurrentDriver();

        if(!currentDriver.getAvailable()){
            throw new RuntimeException("Driver cannot accept ride due to unavailability");
        }
//        currentDriver.setAvailable(false);
        Driver savedDriver = updateDriverAvailability(currentDriver,false);
        Ride ride = rideService.createNewRide(rideRequest,savedDriver);

        return modelMapper.map(ride, RideDto.class);
    }

    @Override
    public RideDto cancelRide(Long rideId) {
        Ride ride = rideService.getRideById(rideId);
        Driver driver = getCurrentDriver();

        if(!driver.equals(ride.getDriver())){
            throw new RuntimeException("Driver cannot start this ride, as he has not accepted earlier");
        }

        if(!ride.getRideStatus().equals(RideStatus.CONFIRMED))
        {
            throw new RuntimeException("Ride can not be cancelled, invalid satus" + ride.getRideStatus());
        }

        rideService.updateRideStatus(ride,RideStatus.CANCELLED);
        updateDriverAvailability(driver,true);

        return modelMapper.map(ride,RideDto.class);
    }

    @Override
    public RideDto startRide(Long rideId,String opt) {
        Ride ride = rideService.getRideById(rideId);
        Driver driver = getCurrentDriver();

        if(!driver.equals(ride.getDriver())){
            throw new RuntimeException("Driver cannot start this ride, as he has not accepted earlier");
        }

        if(!ride.getRideStatus().equals(RideStatus.CONFIRMED))
        {
            throw new RuntimeException("Ride status is not CONFIRMED hence cannot be started " + ride.getRideStatus());
        }
        if(!opt.equals(ride.getOtp())){
            throw new RuntimeException("OTP is not valid :" + opt);
        }
        ride.setStartedAt(LocalDateTime.now());
        Ride savedRide = rideService.updateRideStatus(ride,RideStatus.ONGOING);

        paymentService.createNewPayment(savedRide);
        return modelMapper.map(savedRide,RideDto.class);
    }

    @Override
    @Transactional
    public RideDto endRide(Long rideId) {
        Ride ride = rideService.getRideById(rideId);
        Driver driver = getCurrentDriver();

        if(!driver.equals(ride.getDriver())){
            throw new RuntimeException("Driver cannot start this ride, as he has not accepted earlier");
        }

        if(!ride.getRideStatus().equals(RideStatus.ONGOING))
        {
            throw new RuntimeException("Ride status is not ONGOING hence cannot be ended " + ride.getRideStatus());
        }

        ride.setEndedAt(LocalDateTime.now());
        Ride savedRide = rideService.updateRideStatus(ride,RideStatus.ENDED);
        updateDriverAvailability(driver,true);

        paymentService.processPayment(ride);

        return modelMapper.map(savedRide, RideDto.class);
    }

    @Override
    public RiderDto rateRider(Long rideId, Integer rating) {
        return null;
    }

    @Override
    public DriverDto getMyProfile() {
        Driver currentDriver = getCurrentDriver();
        return modelMapper.map(currentDriver,DriverDto.class);
    }

    @Override
    public Page<RideDto> getAllMyRides(PageRequest pageRequest) {
        Driver currentDriver = getCurrentDriver();
        return rideService.getAllRidesOfDriver(currentDriver, pageRequest)
                .map(ride -> modelMapper.map(ride,RideDto.class));
    }

    @Override
    public Driver getCurrentDriver() {
        return driverRepository.findById(13L)
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found with id " + 13));
    }

    @Override
    public Driver updateDriverAvailability(Driver driver, boolean available) {
        driver.setAvailable(available);
        driverRepository.save(driver);
        return driver;
    }
}
