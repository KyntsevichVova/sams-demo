package com.sams.demo.controller;

import com.sams.demo.entity.Question;
import com.sams.demo.service.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.sams.demo.common.ApplicationConstant.DEFAULT_LIMIT;
import static com.sams.demo.common.ApplicationConstant.DEFAULT_OFFSET;
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
    public ResponseEntity<Page<Question>> findAll(@RequestParam(defaultValue = DEFAULT_OFFSET) String offset,
                                                  @RequestParam(defaultValue = DEFAULT_LIMIT) String limit,
                                                  Pageable pageable) {

        return new ResponseEntity<>(questionService.findAll(pageable), OK);
    }
}