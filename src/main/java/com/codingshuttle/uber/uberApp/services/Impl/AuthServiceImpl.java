package com.codingshuttle.uber.uberApp.services.Impl;

import com.codingshuttle.uber.uberApp.dto.DriverDto;
import com.codingshuttle.uber.uberApp.dto.SignupDto;
import com.codingshuttle.uber.uberApp.dto.UserDto;
import com.codingshuttle.uber.uberApp.entities.User;
import com.codingshuttle.uber.uberApp.entities.enums.Role;
import com.codingshuttle.uber.uberApp.repositories.UserRepository;
import com.codingshuttle.uber.uberApp.services.AuthService;
import com.codingshuttle.uber.uberApp.services.RiderService;
import com.codingshuttle.uber.uberApp.services.WalletService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final RiderService riderService;
    private final WalletService walletService;

    @Override
    public String login(String email, String password) {
        return "";
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
        User savedUser = userRepository.save(mappedUser);

//        user related entities
        riderService.createNewRider(savedUser);
        walletService.createNewWallet(savedUser);

        return modelMapper.map(savedUser,UserDto.class);
    }

    @Override
    public DriverDto onboardNewDriver(Long userId) {
        return null;
    }
}
