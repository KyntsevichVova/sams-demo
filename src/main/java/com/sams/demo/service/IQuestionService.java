package com.sams.demo.service;

import com.sams.demo.data.Question;

import java.util.List;

public interface IQuestionService {

    List<Question> findAll();
}