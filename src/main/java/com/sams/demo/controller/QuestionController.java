package com.sams.demo.controller;

import com.sams.demo.model.dto.QuestionDTO;
import com.sams.demo.model.entity.Question;
import com.sams.demo.model.error.exception.SamsDemoException;
import com.sams.demo.model.mapper.IDTOMapper;
import com.sams.demo.model.response.ResponseBuilder;
import com.sams.demo.model.response.SamsDemoResponse;
import com.sams.demo.service.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/questions")
public class QuestionController {

    private IQuestionService questionService;

    private IDTOMapper<QuestionDTO, Question> questionDTOMapper;

    @Autowired
    public QuestionController(IQuestionService questionService, IDTOMapper<QuestionDTO, Question> questionDTOMapper) {

        this.questionService = questionService;
        this.questionDTOMapper = questionDTOMapper;
    }

    @GetMapping
    public ResponseEntity<SamsDemoResponse<QuestionDTO>> findAll(Pageable pageable) {

        Page<Question> page = questionService.findAll(pageable);

        return ResponseBuilder
                .<QuestionDTO, Question>success()
                .withPageData(page, questionDTOMapper)
                .withHttpStatus(OK)
                .build();
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<QuestionDTO> findById(@PathVariable(name = "questionId") Long questionId) {

        return new ResponseEntity<>(questionService.findById(questionId), OK);
    }

    @PostMapping
    public ResponseEntity<Question> save(@RequestBody @Valid QuestionDTO questionDTO) {

        questionService.save(questionDTO);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{questionId}")
    public ResponseEntity<Question> update(@PathVariable(name = "questionId") Long questionId,
                                           @RequestBody @Valid QuestionDTO questionDTO) throws SamsDemoException {

        questionService.update(questionId, questionDTO);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{questionId}")
    public ResponseEntity<Question> delete(@PathVariable(name = "questionId") Long questionId) {

        questionService.delete(questionId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}