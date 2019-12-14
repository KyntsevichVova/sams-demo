package com.sams.demo.controller;

import com.sams.demo.model.dto.security.SignInRequest;
import com.sams.demo.model.dto.security.SignInResponse;
import com.sams.demo.model.dto.security.SignUpRequest;
import com.sams.demo.model.error.exception.SamsDemoException;
import com.sams.demo.service.IAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class LoginController {

    private AuthenticationManager authenticationManager;
    private IAuthenticationService authenticationService;

    @Autowired
    public LoginController(
            AuthenticationManager authenticationManager,
            IAuthenticationService authenticationService) {

        this.authenticationManager = authenticationManager;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public void signUp(@RequestBody @Valid SignUpRequest signUpRequest) throws SamsDemoException {

        authenticationService.signUp(signUpRequest);
    }

    @PostMapping("/signin")
    public SignInResponse signIn(@RequestBody @Valid SignInRequest signInRequest) throws SamsDemoException {

        return authenticationService.signIn(authenticationManager, signInRequest);
    }
}