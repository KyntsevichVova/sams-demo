package com.sams.demo.controller;

import com.sams.demo.model.dto.security.SignInRequest;
import com.sams.demo.model.dto.security.SignUpRequest;
import com.sams.demo.model.entity.User;
import com.sams.demo.model.error.exception.SamsDemoException;
import com.sams.demo.model.response.ResponseBuilder;
import com.sams.demo.service.IAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.sams.demo.common.ApplicationConstant.USER_ENTITY_LOCATION;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

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
    public ResponseEntity signUp(@RequestBody @Valid SignUpRequest signUpRequest) throws SamsDemoException {

        User user = authenticationService.signUp(authenticationManager, signUpRequest);

        return ResponseBuilder
                .empty()
                .withLocation(USER_ENTITY_LOCATION, user.getId())
                .withAuthorization(authenticationService.signIn(authenticationManager))
                .withHttpStatus(CREATED)
                .build();
    }

    @PostMapping("/signin")
    public ResponseEntity signIn(@RequestBody @Valid SignInRequest signInRequest) throws SamsDemoException {

        return ResponseBuilder
                .empty()
                .withAuthorization(authenticationService.signIn(authenticationManager, signInRequest))
                .withHttpStatus(OK)
                .build();
    }
}