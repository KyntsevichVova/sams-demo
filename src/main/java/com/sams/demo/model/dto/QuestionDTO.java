package com.sams.demo.model.dto;

import com.sams.demo.model.entity.Question;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class QuestionDTO extends BaseDTO {

    private Long id;

    @NotNull
    @NotBlank
    private String title;

    @NotNull
    @NotBlank
    private String link;

    @NotNull
    private Question.Level level;
}