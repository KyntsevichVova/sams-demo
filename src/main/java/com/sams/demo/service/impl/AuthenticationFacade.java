package com.sams.demo.service.impl;

import com.sams.demo.model.entity.Question;
import com.sams.demo.model.entity.RoleCon;
import com.sams.demo.model.entity.User;
import com.sams.demo.model.enums.Role;
import com.sams.demo.model.error.exception.SamsDemoException;
import com.sams.demo.repository.RoleConRepository;
import com.sams.demo.repository.UserRepository;
import com.sams.demo.service.IAuthenticationFacade;
import com.sams.demo.service.IQuestionService;
import com.sams.demo.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.sams.demo.model.enums.Role.USER;
import static com.sams.demo.model.error.ErrorCode.ACCESS_DATABASE_ERROR;
import static com.sams.demo.model.error.exception.SamsDemoException.internalServerException;

@Slf4j
@Service
public class AuthenticationFacade implements IAuthenticationFacade {

    private final IQuestionService questionService;
    private final IUserService userService;
    private final UserRepository userRepository;
    private final RoleConRepository roleConRepository;

    @Autowired
    public AuthenticationFacade(
            IQuestionService questionService,
            IUserService userService,
            UserRepository userRepository,
            RoleConRepository roleConRepository) {

        this.questionService = questionService;
        this.userService = userService;
        this.userRepository = userRepository;
        this.roleConRepository = roleConRepository;
    }

    @Override
    public Question findQuestion(Long questionId) throws SamsDemoException {

        log.debug("Entered [findQuestion] with questionId = {}", questionId);

        return questionService.findByIdAndByPassProxy(questionId);
    }

    @Override
    public User findUser(Long userId) {

        log.debug("Entered [findUser] with userId = {}", userId);

        return userService.findByIdAndByPassProxy(userId);
    }

    @Override
    public User findUser(String email) throws SamsDemoException {

        log.debug("Entered [findUser] with email = {}", email);

        return userService.findByEmail(email);
    }

    @Override
    public User save(User user) throws SamsDemoException {

        try {
            return userRepository.save(user);
        } catch (Exception ex) {

            log.error("Internal server exception [save]: database access error {}", ex.getMessage());
            throw internalServerException(ACCESS_DATABASE_ERROR);
        }
    }

    @Override
    public RoleCon findRoleCon(Role role) throws SamsDemoException {

        try {
            return roleConRepository.findByRole(USER);
        } catch (Exception ex) {

            log.error("Internal server exception [findRoleCon]: database access error {}", ex.getMessage());
            throw internalServerException(ACCESS_DATABASE_ERROR);
        }
    }
}