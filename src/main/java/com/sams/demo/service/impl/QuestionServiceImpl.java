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

import static com.sams.demo.model.error.ErrorCode.*;
import static com.sams.demo.model.error.exception.SamsDemoException.*;

@Service
public class QuestionServiceImpl implements IQuestionService {

    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public Page<Question> findAll(String level, Pageable pageable) {

        return questionRepository.findAll(level, pageable);
    }

    @Override
    public Question findById(Long questionId) throws SamsDemoException {

        Optional<Question> optionalQuestion;

        if (questionId == null) {
            throw badRequestException(ID_MISSING);
        }

        try {
            optionalQuestion = questionRepository.findById(questionId);
        } catch (Exception ex) {
            throw internalServerException(ACCESS_DATABASE_ERROR);
        }

        if (!optionalQuestion.isPresent()) {
            throw entityNotFoundException(
                    ENTITY_NOT_FOUND,
                    Question.class.getSimpleName(),
                    questionId.toString());
        }

        return optionalQuestion.get();
    }

    @Override
    public void save(Question question) {

        questionRepository.save(question);
    }

    @Override
    public Question update(Long questionId, QuestionDTO questionDTO) throws SamsDemoException {

        Question question = findById(questionId);

        //question.setTitle(questionDTO.getTitle());
        question.setLink(questionDTO.getLink());
        //question.setLevel(Level.valueOf(questionDTO.getLevel()));

        questionRepository.save(question);

        return question;
    }

    @Override
    public void delete(Long questionId) {

        questionRepository.deleteById(questionId);
    }
}