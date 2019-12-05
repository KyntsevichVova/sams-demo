package com.sams.demo.service.impl;

import com.sams.demo.model.dto.QuestionDTO;
import com.sams.demo.model.entity.*;
import com.sams.demo.model.error.exception.SamsDemoException;
import com.sams.demo.model.mapper.IDTOMapper;
import com.sams.demo.repository.LevelConRepository;
import com.sams.demo.repository.QuestionRepository;
import com.sams.demo.service.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

import static com.sams.demo.model.error.ErrorCode.*;
import static com.sams.demo.model.error.exception.SamsDemoException.*;
import static java.util.Collections.singletonList;

@Service
public class QuestionServiceImpl implements IQuestionService {

    private final QuestionRepository questionRepository;
    private final LevelConRepository levelConRepository;

    private IDTOMapper<QuestionDTO, Question> questionDTOMapper;

    @Autowired
    public QuestionServiceImpl(
            QuestionRepository questionRepository,
            LevelConRepository levelConRepository,
            IDTOMapper<QuestionDTO, Question> questionDTOMapper) {

        this.questionRepository = questionRepository;
        this.levelConRepository = levelConRepository;
        this.questionDTOMapper = questionDTOMapper;
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
    @Transactional
    public void save(QuestionDTO questionDTO, String locale) throws SamsDemoException {

        LevelCon level = levelConRepository.findByType(questionDTO.getLevel());

        Title title = new Title();
        title.setTitle(questionDTO.getTitle());
        title.setLocale(findRequiredLocale(level, locale));
        title.setId(new TitleId());
        title.getId().setLocaleId(title.getLocale().getId());

        Question question = questionDTOMapper.mapToEntity(questionDTO);
        question.setLevel(level);
        question.setTitles(singletonList(title));
        title.setQuestion(question);

        questionRepository.save(question);
    }

    @Override
    public Question update(Long questionId, QuestionDTO questionDTO, String locale) throws SamsDemoException {

        Question question = findById(questionId);

        Optional<Title> existingTitle = question.getTitles()
                .stream()
                .filter(title -> title.getLocale().getCode().equals(locale))
                .findFirst();

        if (existingTitle.isPresent()) {

            Title title = existingTitle.get();
            title.setTitle(questionDTO.getTitle());

        } else {

            Title title = new Title();
            title.setTitle(questionDTO.getTitle());
            title.setLocale(findRequiredLocale(question.getLevel(), locale));
            title.setId(new TitleId());
            title.getId().setLocaleId(title.getLocale().getId());
            title.getId().setQuestionId(question.getId());
            question.getTitles().add(title);
            title.setQuestion(question);
        }

        questionRepository.save(question);

        return question;
    }

    @Override
    public void delete(Long questionId) {

        questionRepository.deleteById(questionId);
    }

    private Locale findRequiredLocale(LevelCon level, String locale) throws SamsDemoException {

        Optional<Locale> optionalLocale = level
                .getLocalizedLevels()
                .stream()
                .filter(ll -> ll.getLocale().getCode().equals(locale))
                .map(LevelLocalized::getLocale)
                .findAny();

        if (optionalLocale.isPresent()) {
            return optionalLocale.get();
        } else {
            throw badRequestException(LOCALE_NOT_SUPPORTED, locale);
        }
    }
}