package com.sams.demo.controller;

import com.sams.demo.model.dto.CreateQuestionDTO;
import com.sams.demo.model.dto.ReadQuestionDTO;
import com.sams.demo.model.entity.Question;
import com.sams.demo.model.error.exception.SamsDemoException;
import com.sams.demo.model.mapper.IDTOMapper;
import com.sams.demo.model.response.ResponseBuilder;
import com.sams.demo.model.response.SamsDemoResponse;
import com.sams.demo.service.IQuestionService;
import com.sams.demo.web.annotation.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Locale;

import static java.util.Collections.singletonList;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/v1/questions")
public class QuestionController {

    private IQuestionService questionService;

    private IDTOMapper<CreateQuestionDTO, Question> questionDTOMapper;

    @Autowired
    public QuestionController(IQuestionService questionService,
                              IDTOMapper<CreateQuestionDTO, Question> questionDTOMapper) {

        this.questionService = questionService;
        this.questionDTOMapper = questionDTOMapper;
    }

    @GetMapping
    public ResponseEntity<SamsDemoResponse<ReadQuestionDTO>> findAllQuestions(
            @Level String level,
            Locale locale,
            Pageable pageable) {

        Page<ReadQuestionDTO> page = questionService.findAll(level, locale.toLanguageTag(), pageable);

        return ResponseBuilder
                .<ReadQuestionDTO, Question>success()
                .withPageData(page)
                .withHttpStatus(OK)
                .build();
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<SamsDemoResponse<CreateQuestionDTO>> findQuestionById(
            @PathVariable(name = "questionId") Long questionId) throws SamsDemoException {

        Question question = questionService.findById(questionId);

        return ResponseBuilder
                .<CreateQuestionDTO, Question>success()
                .withData(singletonList(question), questionDTOMapper)
                .withHttpStatus(OK)
                .build();
    }

    @PostMapping
    public ResponseEntity createQuestion(
            @RequestBody @Valid CreateQuestionDTO questionDTO,
            Locale locale) throws SamsDemoException {

        questionService.save(questionDTO, locale.toLanguageTag());

        return ResponseBuilder
                .empty()
                .withHttpStatus(CREATED)
                .build();
    }

    @PutMapping("/{questionId}")
    public ResponseEntity<SamsDemoResponse<CreateQuestionDTO>> updateQuestion(
            @PathVariable(name = "questionId") Long questionId,
            @RequestBody @Valid CreateQuestionDTO questionDTO,
            Locale locale) throws SamsDemoException {

        Question question = questionService.update(questionId, questionDTO, locale.toLanguageTag());

        return ResponseBuilder
                .<CreateQuestionDTO, Question>success()
                .withData(singletonList(question), questionDTOMapper)
                .withHttpStatus(OK)
                .build();
    }

    @DeleteMapping("/{questionId}")
    public ResponseEntity deleteQuestion(@PathVariable(name = "questionId") Long questionId) {

        questionService.delete(questionId);

        return ResponseBuilder
                .empty()
                .withHttpStatus(NO_CONTENT)
                .build();
    }
}