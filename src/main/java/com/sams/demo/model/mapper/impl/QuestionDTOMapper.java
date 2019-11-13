package com.sams.demo.model.mapper.impl;

import com.sams.demo.model.dto.QuestionDTO;
import com.sams.demo.model.entity.Question;
import com.sams.demo.model.mapper.IDTOMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class QuestionDTOMapper implements IDTOMapper<QuestionDTO, Question> {

    @Override
    public QuestionDTO mapToDTO(Question question) {

        QuestionDTO dto = new QuestionDTO();

        dto.setId(question.getId());
        dto.setTitle(question.getTitle());
        dto.setLink(question.getLink());
        dto.setLevel(question.getLevel());

        return dto;
    }

    @Override
    public List<QuestionDTO> mapToDTOList(List<Question> questionList) {

        return questionList
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Question mapToEntity(QuestionDTO dto) {

        Question question = new Question();

        question.setId(dto.getId());
        question.setTitle(dto.getTitle());
        question.setLink(dto.getLink());
        question.setLevel(dto.getLevel());

        return question;
    }
}