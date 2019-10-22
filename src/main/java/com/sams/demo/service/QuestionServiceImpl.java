package com.sams.demo.service;

import com.sams.demo.data.Question;
import com.sams.demo.data.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService{

    private final QuestionRepository repository;

    @Autowired
    public QuestionServiceImpl(QuestionRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Question> findAll() {
        return repository.findAll();
    }
}
