package com.sams.demo.model.dto;

import com.sams.demo.model.enums.LevelType;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class ReadQuestionDTO extends BaseDTO {

    private Long id;
    private String link;
    private LevelType level;
    private List<ReadTitleDTO> titles;
}