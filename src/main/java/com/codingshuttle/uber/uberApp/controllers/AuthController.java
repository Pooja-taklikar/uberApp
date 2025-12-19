package com.codingshuttle.uber.uberApp.controllers;

import com.codingshuttle.uber.uberApp.dto.SignupDto;
import com.codingshuttle.uber.uberApp.dto.UserDto;
import com.codingshuttle.uber.uberApp.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    UserDto signUp(@RequestBody SignupDto signupDto){
        return authService.signUp(signupDto);
    }
}
