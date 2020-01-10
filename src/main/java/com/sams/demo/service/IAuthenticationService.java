package com.sams.demo.service;

import com.sams.demo.model.dto.security.SignInRequest;
import com.sams.demo.model.dto.security.SignUpRequest;
import com.sams.demo.model.entity.User;
import com.sams.demo.model.error.exception.SamsDemoException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IAuthenticationService extends UserDetailsService {

    User signUp(AuthenticationManager authenticationManager,
                SignUpRequest signUpRequest) throws SamsDemoException;

    String signIn(AuthenticationManager authenticationManager,
                  SignInRequest signInRequest) throws SamsDemoException;

    String signIn(AuthenticationManager authenticationManager) throws SamsDemoException;

    boolean checkQuestionOwnerShip(Authentication authentication, Long questionId);

    boolean checkUserOwnerShip(Authentication authentication, Long userId);
}