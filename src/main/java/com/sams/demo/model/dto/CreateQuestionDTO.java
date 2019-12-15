package com.sams.demo.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.sams.demo.model.dto.deserializer.LevelTypeDeserializer;
import com.sams.demo.model.dto.deserializer.LocaleDeserializer;
import com.sams.demo.model.dto.validator.annotation.EnumType;
import com.sams.demo.model.enums.LevelType;
import com.sams.demo.model.enums.LocaleEnum;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static com.sams.demo.model.error.ErrorCode.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class CreateQuestionDTO extends BaseDTO {

    @NotNull(message = FIELD_MISSING)
    @NotBlank(message = FIELD_EMPTY)
    @Size(min = 3, max = 255, message = FIELD_INVALID_LENGTH)
    private String title;

    @NotNull(message = FIELD_MISSING)
    @NotBlank(message = FIELD_EMPTY)
    @Size(min = 3, max = 255, message = FIELD_INVALID_LENGTH)
    private String link;

    @NotNull(message = FIELD_MISSING)
    @EnumType(enumClass = LocaleEnum.class, message = FIELD_INVALID_VALUE)
    @JsonDeserialize(using = LocaleDeserializer.class)
    private LocaleEnum locale;

    @NotNull(message = FIELD_MISSING)
    @EnumType(enumClass = LevelType.class, message = FIELD_INVALID_VALUE)
    @JsonDeserialize(using = LevelTypeDeserializer.class)
    private LevelType level;
}