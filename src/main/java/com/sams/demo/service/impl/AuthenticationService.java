package com.sams.demo.service.impl;

import com.sams.demo.model.dto.security.SignInRequest;
import com.sams.demo.model.dto.security.SignInResponse;
import com.sams.demo.model.dto.security.SignUpRequest;
import com.sams.demo.model.entity.RoleCon;
import com.sams.demo.model.entity.User;
import com.sams.demo.model.entity.UserRole;
import com.sams.demo.model.entity.UserRoleId;
import com.sams.demo.model.error.exception.SamsDemoException;
import com.sams.demo.repository.RoleConRepository;
import com.sams.demo.repository.UserRepository;
import com.sams.demo.security.JwtTokenProvider;
import com.sams.demo.service.IAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static com.sams.demo.model.enums.Role.USER;
import static com.sams.demo.model.error.ErrorCode.USER_EMAIL_NOT_FOUND_ERROR;
import static com.sams.demo.model.error.ErrorCode.USER_EXISTS;
import static com.sams.demo.model.error.exception.SamsDemoException.badRequestException;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

@Service
public class AuthenticationService implements IAuthenticationService {

    private UserRepository userRepository;
    private RoleConRepository roleConRepository;
    private JwtTokenProvider jwtTokenProvider;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationService(
            UserRepository userRepository,
            RoleConRepository roleConRepository,
            JwtTokenProvider jwtTokenProvider,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.roleConRepository = roleConRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("ERROR!!!!!!!!!!!!!!!!!!!!11");
        }

        List<GrantedAuthority> authorities = user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole().getRole().name()))
                .collect(toList());;

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(authorities)
                .build();
    }

    @Override
    @Transactional
    public User signUp(AuthenticationManager authenticationManager, SignUpRequest signUpRequest) throws SamsDemoException {

        if(userRepository.findByEmail(signUpRequest.getEmail()) != null) {
            throw badRequestException(USER_EXISTS, signUpRequest.getEmail());
        }

        RoleCon role = roleConRepository.findByRole(USER);

        UserRole userRole = new UserRole();
        userRole.setId(new UserRoleId());
        userRole.getId().setRoleId(role.getId());
        userRole.setRole(role);

        User user = User.builder()
                .email(signUpRequest.getEmail())
                .username(signUpRequest.getUsername())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .roles(singletonList(userRole))
                .build();

        userRole.setUser(user);

        user = userRepository.save(user);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signUpRequest.getEmail(),
                        signUpRequest.getPassword()));

        if (authentication.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        return user;
    }

    @Override
    public SignInResponse signIn(
            AuthenticationManager authenticationManager, SignInRequest signInRequest) {

        Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            signInRequest.getEmail(),
                            signInRequest.getPassword()));

        User user = null;
        if (authentication.isAuthenticated()) {
            user = userRepository.findByEmail(signInRequest.getEmail());
        }

        if (user == null) {
            throw badRequestException(USER_EMAIL_NOT_FOUND_ERROR, signInRequest.getEmail());
        }

        return new SignInResponse(jwtTokenProvider.generateToken(authentication, user));
    }

    @Override
    public String signIn(AuthenticationManager authenticationManager) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        User user = userRepository.findByEmail(authentication.getName());

        if (user == null) {
            throw badRequestException(USER_EMAIL_NOT_FOUND_ERROR, authentication.getName());
        }

        return jwtTokenProvider.generateToken(
                SecurityContextHolder.getContext().getAuthentication(), user);
    }
}