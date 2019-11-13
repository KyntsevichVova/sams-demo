package com.sams.demo.model.mapper;

import com.sams.demo.model.dto.BaseDTO;
import com.sams.demo.model.entity.BaseEntity;

import java.util.List;

public interface IDTOMapper<DTO extends BaseDTO, ENTITY extends BaseEntity> {

    DTO mapToDTO(ENTITY entity);

    List<DTO> mapToDTOList(List<ENTITY> entityList);
}