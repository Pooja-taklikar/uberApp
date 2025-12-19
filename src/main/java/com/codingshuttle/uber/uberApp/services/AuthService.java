package com.codingshuttle.uber.uberApp.services;

import com.codingshuttle.uber.uberApp.dto.DriverDto;
import com.codingshuttle.uber.uberApp.dto.SignupDto;
import com.codingshuttle.uber.uberApp.dto.UserDto;

public interface AuthService {

    String login(String email,String password);

    UserDto signUp(SignupDto signupDto);

    DriverDto onboardNewDriver(Long userId);
}
