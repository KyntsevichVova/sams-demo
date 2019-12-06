package com.sams.demo.model.mapper.impl;

import com.sams.demo.model.dto.CreateQuestionDTO;
import com.sams.demo.model.entity.Question;
import com.sams.demo.model.mapper.IDTOMapper;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class QuestionDTOMapper implements IDTOMapper<CreateQuestionDTO, Question> {

    @Override
    public CreateQuestionDTO mapToDTO(Question question) {

        CreateQuestionDTO dto = new CreateQuestionDTO();

        dto.setLink(question.getLink());

        return dto;
    }

    @Override
    public List<CreateQuestionDTO> mapToDTOList(List<Question> questionList) {

        return questionList
                .stream()
                .map(this::mapToDTO)
                .collect(toList());
    }

    @Override
    public Question mapToEntity(CreateQuestionDTO dto) {

        Question question = new Question();

        question.setLink(dto.getLink());

        return question;
    }
}