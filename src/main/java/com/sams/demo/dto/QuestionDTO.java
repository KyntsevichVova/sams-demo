package com.sams.demo.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class QuestionDTO {

    private int id;

    @NotNull
    @NotBlank
    private String title;

    @NotNull
    @NotBlank
    private String link;

    @NotNull
    @NotBlank
    private String level;
}
