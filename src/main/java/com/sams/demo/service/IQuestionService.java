package com.sams.demo.service;

import com.sams.demo.model.dto.CreateQuestionDTO;
import com.sams.demo.model.dto.ReadAllQuestionDTO;
import com.sams.demo.model.entity.Question;
import com.sams.demo.model.error.exception.SamsDemoException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IQuestionService {

    Page<ReadAllQuestionDTO> findAll(String level, String locale, Pageable pageable);

    Question findById(Long questionId) throws SamsDemoException;

    void save(CreateQuestionDTO questionDTO, String locale) throws SamsDemoException;

    Question update(Long questionId, CreateQuestionDTO questionDTO, String locale) throws SamsDemoException;

    void delete(Long questionId);
}