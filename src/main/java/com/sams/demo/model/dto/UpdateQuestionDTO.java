package com.sams.demo.model.dto;

import com.sams.demo.model.enums.LevelType;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class UpdateQuestionDTO extends BaseDTO {

    private String link;
    private LevelType level;
    private List<TitleDTO> titles;
}