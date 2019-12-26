package com.sams.demo.service.impl;

import com.sams.demo.model.dto.UpdateUserDTO;
import com.sams.demo.model.entity.User;
import com.sams.demo.model.error.exception.SamsDemoException;
import com.sams.demo.repository.UserRepository;
import com.sams.demo.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.sams.demo.model.error.ErrorCode.*;
import static com.sams.demo.model.error.exception.SamsDemoException.*;
import static com.sams.demo.security.SecurityExpression.USER_ACL;

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
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<User> findAll(Pageable pageable) {

        log.debug("Entered [findAll] with pageable = {}", pageable);

        try {
            return userRepository.findAll(pageable);
        } catch (Exception ex) {
            throw internalServerException(ACCESS_DATABASE_ERROR);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize(USER_ACL)
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
    public User findByIdAndByPassProxy(Long userId) throws SamsDemoException {
        return this.findById(userId);
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

    @Override
    @Transactional
    @PreAuthorize(USER_ACL)
    public User update(Long userId, UpdateUserDTO userDTO) throws SamsDemoException {

        log.debug("Entered [update] with userId = {}, userDTO = {}", userId, userDTO);

        User user = findById(userId);

        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());

        try {
            userRepository.save(user);
        } catch (Exception ex) {
            throw internalServerException(ACCESS_DATABASE_ERROR);
        }

        log.debug("Exited [update] with user = {}", user);

        return user;
    }

    @Override
    @PreAuthorize(USER_ACL)
    public void delete(Long userId) throws SamsDemoException {

        log.debug("Entered [delete]");

        try {
            userRepository.deleteById(userId);
        } catch (EmptyResultDataAccessException ex) {

            throw entityNotFoundException(
                    ENTITY_NOT_FOUND,
                    User.class.getSimpleName(),
                    userId.toString());
        }

        log.debug("Exited [delete]");
    }
}