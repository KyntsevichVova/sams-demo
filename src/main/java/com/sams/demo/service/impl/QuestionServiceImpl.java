package com.sams.demo.service.impl;

import com.sams.demo.data.Question;
import com.sams.demo.repository.QuestionRepository;
import com.sams.demo.service.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl implements IQuestionService {

    private final QuestionRepository repository;

    @Autowired
    public QuestionServiceImpl(QuestionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<Question> findAll(Pageable pageable) {

        return repository.findAll(pageable);
    }
}