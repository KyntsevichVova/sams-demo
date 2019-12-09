package com.sams.demo.model.mapper.impl;

import com.sams.demo.model.dto.ReadQuestionDTO;
import com.sams.demo.model.dto.ReadTitleDTO;
import com.sams.demo.model.entity.Question;
import com.sams.demo.model.entity.Title;
import com.sams.demo.model.mapper.IDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReadQuestionDTOMapper implements IDTOMapper<ReadQuestionDTO, Question> {

    private IDTOMapper<ReadTitleDTO, Title> titleDTOMapper;

    @Autowired
    public ReadQuestionDTOMapper(IDTOMapper<ReadTitleDTO, Title> titleDTOMapper) {
        this.titleDTOMapper = titleDTOMapper;
    }

    @Override
    public ReadQuestionDTO mapToDTO(Question entity) {

        ReadQuestionDTO dto = new ReadQuestionDTO();

        dto.setId(entity.getId());
        dto.setLink(entity.getLink());
        //TODO enum or localized value ?
        dto.setLevel(entity.getLevel().getType().name());
        dto.setTitles(titleDTOMapper.mapToDTOList(entity.getTitles()));

        return dto;
    }

    @Override
    public Question mapToEntity(ReadQuestionDTO entity) {
        return null;
    }
}