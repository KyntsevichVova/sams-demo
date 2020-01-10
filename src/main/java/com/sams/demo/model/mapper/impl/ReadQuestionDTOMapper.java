package com.sams.demo.model.mapper.impl;

import com.sams.demo.model.dto.ReadQuestionDTO;
import com.sams.demo.model.dto.TitleDTO;
import com.sams.demo.model.entity.Question;
import com.sams.demo.model.entity.Title;
import com.sams.demo.model.mapper.IDTOMapper;
import com.sams.demo.security.SecurityPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import static com.sams.demo.common.ApplicationConstant.ANONYMOUS_USER_ID;

@Component
public class ReadQuestionDTOMapper implements IDTOMapper<ReadQuestionDTO, Question> {

    private IDTOMapper<TitleDTO, Title> titleDTOMapper;

    @Autowired
    public ReadQuestionDTOMapper(IDTOMapper<TitleDTO, Title> titleDTOMapper) {
        this.titleDTOMapper = titleDTOMapper;
    }

    @Override
    public ReadQuestionDTO mapToDTO(Question entity) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Long userId = ANONYMOUS_USER_ID;
        if (authentication != null &&
                authentication.getPrincipal() != null &&
                !(authentication instanceof AnonymousAuthenticationToken)) {

            userId = ((SecurityPrincipal)authentication.getPrincipal()).getUserId();
        }

        ReadQuestionDTO dto = new ReadQuestionDTO();

        dto.setId(entity.getId());
        dto.setLink(entity.getLink());
        dto.setLevel(entity.getLevel().getType());
        dto.setTitles(titleDTOMapper.mapToDTOList(entity.getTitles()));
        dto.setIsOwner(userId.equals(entity.getUser().getId()));

        return dto;
    }
}