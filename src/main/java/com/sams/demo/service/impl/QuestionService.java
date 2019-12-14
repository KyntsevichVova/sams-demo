package com.sams.demo.service.impl;

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
public class QuestionService implements IQuestionService {

    private final QuestionRepository questionRepository;
    private final LevelConRepository levelConRepository;

    private IDTOMapper<CreateQuestionDTO, Question> questionDTOMapper;

    @Autowired
    public QuestionService(
            QuestionRepository questionRepository,
            LevelConRepository levelConRepository,
            IDTOMapper<CreateQuestionDTO, Question> questionDTOMapper) {

        this.questionRepository = questionRepository;
        this.levelConRepository = levelConRepository;
        this.questionDTOMapper = questionDTOMapper;
    }

    @Override
    public Page<ReadAllQuestionDTO> findAll(String level, String locale, Pageable pageable) {

        return questionRepository.findAll(level, locale, pageable);
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
    public Question save(CreateQuestionDTO questionDTO) throws SamsDemoException {

        LevelCon level = levelConRepository.findByType(questionDTO.getLevel());

        Title title = new Title();
        title.setTitle(questionDTO.getTitle());
        title.setLocale(findRequiredLocale(level, questionDTO.getLocale().getValue()));
        title.setId(new TitleId());
        title.getId().setLocaleId(title.getLocale().getId());

        Question question = questionDTOMapper.mapToEntity(questionDTO);
        question.setLevel(level);
        question.setTitles(singletonList(title));
        title.setQuestion(question);

        return questionRepository.save(question);
    }

    @Override
    @Transactional
    public Question update(Long questionId, UpdateQuestionDTO questionDTO) throws SamsDemoException {

        Question question = findById(questionId);

        LevelCon level = levelConRepository.findByType(questionDTO.getLevel());

        questionDTO.getTitles()
                .forEach(titleDTO -> updateTitle(question, titleDTO));

        question.setLink(questionDTO.getLink());
        question.setLevel(level);

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