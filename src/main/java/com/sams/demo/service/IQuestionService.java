package com.sams.demo.service;

import com.sams.demo.model.dto.QuestionDTO;
import com.sams.demo.model.entity.Question;
import com.sams.demo.model.error.exception.SamsDemoException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IQuestionService {

    Page<Question> findAll(String level, Pageable pageable);

    Question findById(Long questionId) throws SamsDemoException;

    void save(Question question);

    Question update(Long questionId, QuestionDTO questionDTO) throws SamsDemoException;

    void delete(Long questionId);
}