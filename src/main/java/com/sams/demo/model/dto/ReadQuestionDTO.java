package com.sams.demo.model.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class ReadQuestionDTO extends BaseDTO {

    private Long id;
    private String title;
    private String link;
    private String level;
}