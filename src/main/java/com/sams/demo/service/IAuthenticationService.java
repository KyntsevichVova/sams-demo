package com.sams.demo.service;

import com.sams.demo.model.dto.security.SignInRequest;
import com.sams.demo.model.dto.security.SignInResponse;
import com.sams.demo.model.dto.security.SignUpRequest;
import com.sams.demo.model.entity.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IAuthenticationService extends UserDetailsService {

    User signUp(SignUpRequest signUpRequest);

    SignInResponse signIn(AuthenticationManager authenticationManager, SignInRequest signInRequest);
}