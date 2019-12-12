package com.sams.demo.model.mapper;

import com.sams.demo.model.dto.BaseDTO;
import com.sams.demo.model.entity.BaseEntity;

import java.util.List;

import static java.util.stream.Collectors.toList;

public interface IDTOMapper<DTO extends BaseDTO, ENTITY extends BaseEntity> {

    DTO mapToDTO(ENTITY entity);

    default List<DTO> mapToDTOList(List<ENTITY> entityList) {

        return entityList
                .stream()
                .map(this::mapToDTO)
                .collect(toList());
    }

    ENTITY mapToEntity(DTO entity);
}