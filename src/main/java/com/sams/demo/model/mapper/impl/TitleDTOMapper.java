package com.sams.demo.model.mapper.impl;

import com.sams.demo.model.dto.TitleDTO;
import com.sams.demo.model.entity.Title;
import com.sams.demo.model.mapper.IDTOMapper;
import org.springframework.stereotype.Component;

import static com.sams.demo.model.enums.LocaleEnum.fromCode;

@Component
public class TitleDTOMapper implements IDTOMapper<TitleDTO, Title> {

    @Override
    public TitleDTO mapToDTO(Title entity) {

        TitleDTO dto = new TitleDTO();

        dto.setTitle(entity.getTitle());
        dto.setLocale(fromCode(entity.getLocale().getCode()));

        return dto;
    }

    @Override
    public Title mapToEntity(TitleDTO entity) {
        return null;
    }
}