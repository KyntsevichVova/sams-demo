package com.sams.demo.model.dto;

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
    private String level;
    private List<ReadTitleDTO> titles;
}