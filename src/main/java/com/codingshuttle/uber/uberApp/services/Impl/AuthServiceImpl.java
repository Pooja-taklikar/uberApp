package com.codingshuttle.uber.uberApp.services.Impl;

import com.codingshuttle.uber.uberApp.dto.DriverDto;
import com.codingshuttle.uber.uberApp.dto.SignupDto;
import com.codingshuttle.uber.uberApp.dto.UserDto;
import com.codingshuttle.uber.uberApp.entities.Driver;
import com.codingshuttle.uber.uberApp.entities.User;
import com.codingshuttle.uber.uberApp.entities.enums.Role;
import com.codingshuttle.uber.uberApp.exceptions.ResourceNotFoundException;
import com.codingshuttle.uber.uberApp.exceptions.RunTimeConflictException;
import com.codingshuttle.uber.uberApp.repositories.UserRepository;
import com.codingshuttle.uber.uberApp.security.JWTService;
import com.codingshuttle.uber.uberApp.services.AuthService;
import com.codingshuttle.uber.uberApp.services.DriverService;
import com.codingshuttle.uber.uberApp.services.RiderService;
import com.codingshuttle.uber.uberApp.services.WalletService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static com.codingshuttle.uber.uberApp.entities.enums.Role.DRIVER;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final RiderService riderService;
    private final WalletService walletService;
    private final DriverService driverService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    @Override
    public String[] login(String email, String password) {


        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email,password)
        );

        User user = (User) authentication.getPrincipal();

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);


        return new String[]{accessToken,refreshToken};
    }

    @Override
    @Transactional
    public UserDto signUp(SignupDto signupDto) {
        User user = userRepository.findByEmail(signupDto.getEmail()).orElse(null);

        if(user != null){
            throw new RuntimeException("Cannot signUp, user already exist with Email: "+ signupDto.getEmail());
        }

        User mappedUser = modelMapper.map(signupDto, User.class);
        mappedUser.setRoles(Set.of(Role.RIDER));
        mappedUser.setPassword(passwordEncoder.encode(mappedUser.getPassword()));
        User savedUser = userRepository.save(mappedUser);

//        user related entities
        riderService.createNewRider(savedUser);
        walletService.createNewWallet(savedUser);

        return modelMapper.map(savedUser,UserDto.class);
    }

    @Override
    public DriverDto onboardNewDriver(Long userId,String vehicleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        if(user.getRoles().contains(DRIVER))
            throw new RunTimeConflictException("User with id "+ userId +" is already driver");

        Driver createDriver = Driver.builder()
                .user(user)
                .rating(0.0)
                .vehicleId(vehicleId)
                .available(true)
                .build();
        user.getRoles().add(DRIVER);
        userRepository.save(user);
        Driver savedDriver = driverService.createNewDriver(createDriver);
        return modelMapper.map(savedDriver, DriverDto.class);
    }

    @Override
    public String refreshToken(String refreshToken) {
        Long userId = jwtService.getUserIdFromToken(refreshToken);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: "+ userId));

        return jwtService.generateAccessToken(user);
    }
}
