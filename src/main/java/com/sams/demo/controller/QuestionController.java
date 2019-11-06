package com.sams.demo.controller;

import com.sams.demo.dto.QuestionDTO;
import com.sams.demo.entity.Question;
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

    @Autowired
    public QuestionController(IQuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping
    public ResponseEntity<Page<Question>> findAll(Pageable pageable) {

        return new ResponseEntity<>(questionService.findAll(pageable), OK);
    }

    @PostMapping
    public ResponseEntity<Question> save(@RequestBody @Valid QuestionDTO questionDTO) {

        questionService.save(questionDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Question> update(@RequestBody @Valid QuestionDTO questionDTO) {

        questionService.update(questionDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{questionId}")
    public ResponseEntity<Question> delete(@PathVariable(name = "questionId") int questionId) {

        questionService.delete(questionId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}