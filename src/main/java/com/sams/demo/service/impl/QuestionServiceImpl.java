package com.sams.demo.service.impl;

import com.sams.demo.dto.QuestionDTO;
import com.sams.demo.entity.Question;
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

    @Override
    public QuestionDTO findById(int questionId) {

        Question question = repository.findById(questionId).get();
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setId(question.getId());
        questionDTO.setTitle(question.getTitle());
        questionDTO.setLink(question.getLink());
        questionDTO.setLevel(question.getLevel());

        return questionDTO;
    }

    @Override
    public void save(QuestionDTO questionDTO) {

        Question question = new Question();
        question.setTitle(questionDTO.getTitle());
        question.setLink(questionDTO.getLink());
        question.setLevel(questionDTO.getLevel());

        repository.save(question);
    }

    @Override
    public void update(QuestionDTO questionDTO) {

        Question question = new Question();
        question.setId(questionDTO.getId());
        question.setTitle(questionDTO.getTitle());
        question.setLink(questionDTO.getLink());
        question.setLevel(questionDTO.getLevel());

        repository.save(question);
    }

    @Override
    public void delete(int questionId) {

        repository.deleteById(questionId);
    }
}