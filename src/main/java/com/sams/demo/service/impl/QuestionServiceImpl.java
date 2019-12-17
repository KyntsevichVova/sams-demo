package com.sams.demo.service.impl;

import com.sams.demo.common.ApplicationConstant;
import com.sams.demo.model.dto.CreateQuestionDTO;
import com.sams.demo.model.dto.ReadAllQuestionDTO;
import com.sams.demo.model.dto.TitleDTO;
import com.sams.demo.model.dto.UpdateQuestionDTO;
import com.sams.demo.model.entity.*;
import com.sams.demo.model.error.exception.SamsDemoException;
import com.sams.demo.model.mapper.IDTOMapper;
import com.sams.demo.repository.LevelConRepository;
import com.sams.demo.repository.QuestionRepository;
import com.sams.demo.service.IQuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

import static com.sams.demo.model.error.ErrorCode.*;
import static com.sams.demo.model.error.exception.SamsDemoException.*;
import static java.util.Collections.singletonList;

@Slf4j
@Service
public class QuestionServiceImpl implements IQuestionService {

    private final QuestionRepository questionRepository;
    private final LevelConRepository levelConRepository;

    private IDTOMapper<CreateQuestionDTO, Question> questionDTOMapper;

    @Autowired
    public QuestionServiceImpl(
            QuestionRepository questionRepository,
            LevelConRepository levelConRepository,
            IDTOMapper<CreateQuestionDTO, Question> questionDTOMapper) {
        this.questionRepository = questionRepository;
        this.levelConRepository = levelConRepository;
        this.questionDTOMapper = questionDTOMapper;
    }

    @Override
    public Page<ReadAllQuestionDTO> findAll(String level, String locale, Pageable pageable) {

        log.debug("Entered [findAll] with level = {}, locale = {}, pageable = {}", level, locale, pageable);

        return questionRepository.findAll(level, locale, pageable);
    }

    @Override
    public Question findById(Long questionId) throws SamsDemoException {

        log.debug("Entered [findById] with questionId = {}", questionId);

        Optional<Question> optionalQuestion;

        if (questionId == null) {
            log.error("Bad request exception: ID is missing");
            throw badRequestException(ID_MISSING);
        }

        try {
            optionalQuestion = questionRepository.findById(questionId);
        } catch (Exception ex) {
            log.error("Internal server exception: database access error");
            throw internalServerException(ACCESS_DATABASE_ERROR);
        }

        if (!optionalQuestion.isPresent()) {
            log.error("Entity not found exception: {}, {}", Question.class.getSimpleName(), questionId.toString());
            throw entityNotFoundException(
                    ENTITY_NOT_FOUND,
                    Question.class.getSimpleName(),
                    questionId.toString());
        }

        log.debug("Exited [findById] with question = {}", optionalQuestion.get());

        return optionalQuestion.get();
    }

    @Override
    @Transactional
    public Question save(CreateQuestionDTO questionDTO) throws SamsDemoException {

        log.debug("Entered [save] with questionDTO = {}", questionDTO);

        LevelCon level = levelConRepository.findByType(questionDTO.getLevel());

        Title title = new Title();
        title.setTitle(questionDTO.getTitle());
        title.setLocale(findRequiredLocale(level, questionDTO.getLocale().getValue()));
        title.setId(new TitleId());
        title.getId().setLocaleId(title.getLocale().getId());

        Question question = questionDTOMapper.mapToEntity(questionDTO);
        question.setLevel(level);
        question.setTitles(singletonList(title));
        question.setIsFullyLocalized(false);

        title.setQuestion(question);

        log.debug("Exited [save]");

        return questionRepository.save(question);
    }

    @Override
    @Transactional
    public Question update(Long questionId, UpdateQuestionDTO questionDTO) throws SamsDemoException {

        log.debug("Entered [update] with questionId = {}, questionDTO = {}", questionId, questionDTO);

        Question question = findById(questionId);

        LevelCon level = levelConRepository.findByType(questionDTO.getLevel());

        questionDTO.getTitles()
                .forEach(titleDTO -> updateTitle(question, titleDTO));

        question.setLink(questionDTO.getLink());
        question.setLevel(level);
        question.setIsFullyLocalized(question.getTitles().size() == ApplicationConstant.SUPPORTED_LOCALES);

        questionRepository.save(question);

        log.debug("Exited [update] with question = {}", question);

        return question;
    }

    @Override
    public void delete(Long questionId) {

        log.debug("Entered [delete]");

        questionRepository.deleteById(questionId);

        log.debug("Exited [delete]");

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
            log.error("Locale not supported exception: {}", locale);
            throw badRequestException(LOCALE_NOT_SUPPORTED, locale);
        }
    }

    private void updateTitle(Question question, TitleDTO titleDTO) {

        Optional<Title> existingTitle = question.getTitles()
                .stream()
                .filter(title -> title.getLocale().getCode().equals(titleDTO.getLocale().getValue()))
                .findFirst();

        if (existingTitle.isPresent()) {

            Title title = existingTitle.get();
            title.setTitle(titleDTO.getTitle());

        } else {

            Title title = new Title();
            title.setTitle(titleDTO.getTitle());
            title.setLocale(findRequiredLocale(question.getLevel(), titleDTO.getLocale().getValue()));
            title.setId(new TitleId());
            title.getId().setLocaleId(title.getLocale().getId());
            title.getId().setQuestionId(question.getId());
            question.getTitles().add(title);
            title.setQuestion(question);

        }
    }
}