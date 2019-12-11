package com.sams.demo.model.dto;

import com.sams.demo.model.enums.LocaleEnum;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class ReadTitleDTO extends BaseDTO {

    private String title;
    private LocaleEnum locale;
}