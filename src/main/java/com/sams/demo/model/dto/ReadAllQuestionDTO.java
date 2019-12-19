package com.sams.demo.model.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class ReadAllQuestionDTO extends BaseDTO {

    private Long id;
    private String title;
    private String link;
    private String level;
    private boolean isFullyLocalized;
    private Boolean isOwner;
}