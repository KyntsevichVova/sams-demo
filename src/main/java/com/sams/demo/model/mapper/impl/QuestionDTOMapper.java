package com.sams.demo.model.mapper.impl;

import com.sams.demo.model.dto.CreateQuestionDTO;
import com.sams.demo.model.entity.Question;
import com.sams.demo.model.mapper.IDTOMapper;
import org.springframework.stereotype.Component;

@Component
public class QuestionDTOMapper implements IDTOMapper<CreateQuestionDTO, Question> {

    @Override
    public Question mapToEntity(CreateQuestionDTO dto) {

        Question question = new Question();

        question.setLink(dto.getLink());

        return question;
    }
}