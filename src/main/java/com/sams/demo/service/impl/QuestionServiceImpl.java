package com.sams.demo.service.impl;

import com.sams.demo.model.dto.QuestionDTO;
import com.sams.demo.model.entity.Question;
import com.sams.demo.model.error.exception.SamsDemoException;
import com.sams.demo.repository.QuestionRepository;
import com.sams.demo.service.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QuestionServiceImpl implements IQuestionService {

    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public Page<Question> findAll(String difficulty, Pageable pageable) {

        return questionRepository.findAll(difficulty, pageable);
    }

    @Override
    public Question findById(Long questionId) throws SamsDemoException {

        Optional<Question> optionalQuestion;

        try {
            optionalQuestion = questionRepository.findById(questionId);
        } catch (Exception ex) {
            throw new SamsDemoException("ERROR");
        }

        if (!optionalQuestion.isPresent()) {
            throw new SamsDemoException("ERROR");
        }

        return optionalQuestion.get();
    }

    @Override
    public void save(Question question) {

        questionRepository.save(question);
    }

    @Override
    public Question update(Long questionId, QuestionDTO questionDTO) throws SamsDemoException {

        Optional<Question> optionalQuestion;

        try {
            optionalQuestion = questionRepository.findById(questionId);
        } catch (Exception ex) {
            throw new SamsDemoException("ERROR");
        }

        if (!optionalQuestion.isPresent()) {
            throw new SamsDemoException("ERROR");
        }

        Question question = optionalQuestion.get();

        question.setTitle(questionDTO.getTitle());
        question.setLink(questionDTO.getLink());
        question.setDifficulty(questionDTO.getDifficulty());

        questionRepository.save(question);

        return question;
    }

    @Override
    public void delete(Long questionId) {

        questionRepository.deleteById(questionId);
    }
}