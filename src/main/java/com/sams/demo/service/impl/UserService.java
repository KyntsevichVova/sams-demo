package com.sams.demo.service.impl;

import com.sams.demo.model.entity.User;
import com.sams.demo.model.error.exception.SamsDemoException;
import com.sams.demo.repository.UserRepository;
import com.sams.demo.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.sams.demo.model.error.ErrorCode.*;
import static com.sams.demo.model.error.exception.SamsDemoException.*;

@Slf4j
@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(Long userId) throws SamsDemoException {

        log.debug("Entered [findById] with userId = {}", userId);

        if (userId == null) {
            log.error("Bad request exception: ID is missing");
            throw badRequestException(ID_MISSING);
        }

        Optional<User> optionalUser;
        try {
            optionalUser = userRepository.findById(userId);
        } catch (Exception ex) {
            log.error("Internal server exception: database access error");
            throw internalServerException(ACCESS_DATABASE_ERROR);
        }

        if (!optionalUser.isPresent()) {
            log.error("Entity not found exception: {}, {}", User.class.getSimpleName(), userId.toString());
            throw entityNotFoundException(
                    ENTITY_NOT_FOUND,
                    User.class.getSimpleName(),
                    userId.toString());
        }

        log.debug("Exited [findById] with user = {}", optionalUser.get());

        return optionalUser.get();
    }

    @Override
    @Transactional(readOnly = true)
    public User findByEmail(String email) throws SamsDemoException {

        try {
            return userRepository.findByEmail(email);
        } catch (Exception ex) {
            throw internalServerException(ACCESS_DATABASE_ERROR);
        }
    }
}