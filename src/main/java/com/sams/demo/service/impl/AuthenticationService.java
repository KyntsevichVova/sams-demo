package com.sams.demo.service.impl;

import com.sams.demo.model.dto.security.SignInRequest;
import com.sams.demo.model.dto.security.SignUpRequest;
import com.sams.demo.model.entity.*;
import com.sams.demo.model.error.exception.SamsDemoException;
import com.sams.demo.security.JwtTokenProvider;
import com.sams.demo.security.SecurityPrincipal;
import com.sams.demo.service.IAuthenticationFacade;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static com.sams.demo.model.enums.Role.USER;
import static com.sams.demo.model.error.ErrorCode.UNEXPECTED_AUTHENTICATION_ERROR;
import static com.sams.demo.model.error.ErrorCode.USER_EXISTS;
import static com.sams.demo.model.error.exception.SamsDemoException.badRequestException;
import static com.sams.demo.model.error.exception.SamsDemoException.internalServerException;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

@Service
public class AuthenticationService implements IAuthenticationService {

    private IAuthenticationFacade authenticationFacade;
    private JwtTokenProvider jwtTokenProvider;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationService(
            IAuthenticationFacade authenticationFacade,
            JwtTokenProvider jwtTokenProvider,
            PasswordEncoder passwordEncoder) {

        this.authenticationFacade = authenticationFacade;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = authenticationFacade.findUser(email);

        if (user == null) {
            throw new UsernameNotFoundException(email);
        }

        List<GrantedAuthority> authorities = user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleCon().getRole().name()))
                .collect(toList());

        return new SecurityPrincipal(user, authorities);
    }

    @Override
    @Transactional
    public User signUp(AuthenticationManager authenticationManager,
                       SignUpRequest signUpRequest) throws SamsDemoException {

        User existingUser = authenticationFacade.findUser(signUpRequest.getEmail());

        if(existingUser != null && !existingUser.getIsDeleted()) {
            throw badRequestException(USER_EXISTS, signUpRequest.getEmail());
        }

        RoleCon roleCon = authenticationFacade.findRoleCon(USER);

        if (roleCon == null) {
            throw internalServerException(UNEXPECTED_AUTHENTICATION_ERROR);
        }

        UserRole userRole = new UserRole();
        userRole.setId(new UserRoleId());
        userRole.getId().setRoleId(roleCon.getId());
        userRole.setRoleCon(roleCon);

        User user = User.builder()
                .email(signUpRequest.getEmail())
                .username(signUpRequest.getUsername())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .roles(singletonList(userRole))
                .questions(emptyList())
                .isDeleted(false)
                .build();

        if(existingUser != null && existingUser.getIsDeleted()) {
            user.setId(existingUser.getId());
        }

        userRole.setUser(user);

        user = authenticationFacade.save(user);

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
    public String signIn(AuthenticationManager authenticationManager,
                         SignInRequest signInRequest) throws SamsDemoException {

        Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            signInRequest.getEmail(),
                            signInRequest.getPassword()));

        User user = null;
        if (authentication.isAuthenticated()) {

            user = authenticationFacade.findUser(signInRequest.getEmail());

            if (user == null) {
                throw internalServerException(UNEXPECTED_AUTHENTICATION_ERROR);
            }
        }

        return jwtTokenProvider.generateToken(authentication, requireNonNull(user));
    }

    @Override
    public String signIn(AuthenticationManager authenticationManager) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        User user = authenticationFacade.findUser(authentication.getName());

        if (user == null) {
            throw internalServerException(UNEXPECTED_AUTHENTICATION_ERROR);
        }

        return jwtTokenProvider.generateToken(
                SecurityContextHolder.getContext().getAuthentication(), user);
    }

    @Override
    @Transactional
    public boolean checkQuestionOwnerShip(Authentication authentication, Long questionId) {

        SecurityPrincipal principal = (SecurityPrincipal) authentication.getPrincipal();

        if (principal == null || principal.getUserId() == null || questionId == null) {
            throw internalServerException(UNEXPECTED_AUTHENTICATION_ERROR);
        }

        Question question = authenticationFacade.findQuestion(questionId);

        User user = authenticationFacade.findUser(principal.getUserId());

        if (user == null || question == null) {
            throw internalServerException(UNEXPECTED_AUTHENTICATION_ERROR);
        }

        return user.getQuestions().stream()
                .map(Question::getId)
                .anyMatch(id -> id.equals(questionId));
    }

    @Override
    @Transactional
    public boolean checkUserOwnerShip(Authentication authentication, Long userId) {

        SecurityPrincipal principal = (SecurityPrincipal) authentication.getPrincipal();

        if (principal == null || principal.getUserId() == null || userId == null) {
            throw internalServerException(UNEXPECTED_AUTHENTICATION_ERROR);
        }

        User user = authenticationFacade.findUser(userId);

        User authenticatedUser = authenticationFacade.findUser(principal.getUserId());

        if (user == null || authenticatedUser == null) {
            throw internalServerException(UNEXPECTED_AUTHENTICATION_ERROR);
        }

        return Objects.equals(user.getId(), authenticatedUser.getId());
    }
}