package com.sams.demo.service;

import com.sams.demo.model.entity.User;
import com.sams.demo.model.error.exception.SamsDemoException;

public interface IUserService {

    User findById(Long userId) throws SamsDemoException;

    User findByEmail(String email) throws SamsDemoException;
}