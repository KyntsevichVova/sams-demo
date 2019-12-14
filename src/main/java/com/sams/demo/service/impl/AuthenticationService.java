package com.sams.demo.service.impl;

import com.sams.demo.model.dto.security.SignInRequest;
import com.sams.demo.model.dto.security.SignInResponse;
import com.sams.demo.model.dto.security.SignUpRequest;
import com.sams.demo.model.entity.User;
import com.sams.demo.model.error.exception.SamsDemoException;
import com.sams.demo.repository.UserRepository;
import com.sams.demo.security.JwtTokenProvider;
import com.sams.demo.service.IAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static com.sams.demo.model.error.ErrorCode.USER_EXISTS;

@Service
//@Primary
public class AuthenticationService implements IAuthenticationService {

    private JwtTokenProvider jwtTokenProvider;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationService(
            UserRepository userRepository,
            JwtTokenProvider jwtTokenProvider,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("ERROR!!!!!!!!!!!!!!!!!!!!11");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                new ArrayList<>());
    }

    @Override
    public User signUp(SignUpRequest signUpRequest) throws SamsDemoException {

        if(userRepository.findByEmail(signUpRequest.getEmail()) != null) {
            throw SamsDemoException.badRequestException(USER_EXISTS, signUpRequest.getEmail());
        }

        User user = User.builder()
                .email(signUpRequest.getEmail())
                .username(signUpRequest.getUsername())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .build();

        //TODO handle roles

        return userRepository.save(user);
    }

    @Override
    public SignInResponse signIn(
            AuthenticationManager authenticationManager, SignInRequest signInRequest) {

        Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            signInRequest.getEmail(),
                            signInRequest.getPassword()));
        //TODO seems redundant
        //SecurityContextHolder.getContext().setAuthentication(authentication);

        return new SignInResponse(jwtTokenProvider.generateToken(authentication));
    }
}