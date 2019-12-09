package com.sams.demo.model.mapper.impl;

import com.sams.demo.model.dto.ReadTitleDTO;
import com.sams.demo.model.entity.Title;
import com.sams.demo.model.mapper.IDTOMapper;
import org.springframework.stereotype.Component;

@Component
public class ReadTitleDTOMapper implements IDTOMapper<ReadTitleDTO, Title> {

    @Override
    public ReadTitleDTO mapToDTO(Title entity) {

        ReadTitleDTO dto = new ReadTitleDTO();

        dto.setTitle(entity.getTitle());
        dto.setLocale(entity.getLocale().getCode());

        return dto;
    }

    @Override
    public Title mapToEntity(ReadTitleDTO entity) {
        return null;
    }
}