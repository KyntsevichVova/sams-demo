package com.sams.demo.model.mapper.impl;

import com.sams.demo.model.dto.QuestionDTO;
import com.sams.demo.model.entity.Question;
import com.sams.demo.model.mapper.IDTOMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class QuestionDTOMapper implements IDTOMapper<QuestionDTO, Question> {

    public QuestionDTO mapToDTO(Question question) {

        QuestionDTO dto = new QuestionDTO();

        dto.setId(question.getId());
        dto.setTitle(question.getTitle());
        dto.setLink(question.getLink());
        dto.setLevel(question.getLevel());

        return dto;
    }

    public List<QuestionDTO> mapToDTOList(List<Question> questionList) {

        return questionList
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
}