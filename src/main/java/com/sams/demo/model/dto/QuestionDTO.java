package com.sams.demo.model.dto;

import com.sams.demo.model.entity.LevelCon.LevelType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static com.sams.demo.model.error.ErrorCode.FIELD_EMPTY;
import static com.sams.demo.model.error.ErrorCode.FIELD_MISSING;

@Data
public class QuestionDTO extends BaseDTO {

    private Long id;

    @NotNull(message = FIELD_MISSING)
    @NotBlank(message = FIELD_EMPTY)
    private String title;

    @NotNull(message = FIELD_MISSING)
    @NotBlank(message = FIELD_EMPTY)
    private String link;

    private LevelType level;
}