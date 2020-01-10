package com.sams.demo.service;

import com.sams.demo.model.entity.Question;
import com.sams.demo.model.entity.RoleCon;
import com.sams.demo.model.entity.User;
import com.sams.demo.model.enums.Role;
import com.sams.demo.model.error.exception.SamsDemoException;

public interface IAuthenticationFacade {

    Question findQuestion(Long questionId) throws SamsDemoException;

    User findUser(Long userId) throws SamsDemoException;

    User findUser(String email) throws SamsDemoException;

    User save(User user) throws SamsDemoException;

    RoleCon findRoleCon(Role role) throws SamsDemoException;
}