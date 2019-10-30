package com.sams.demo.service;

import com.sams.demo.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IQuestionService {

    Page<Question> findAll(Pageable pageable);
}