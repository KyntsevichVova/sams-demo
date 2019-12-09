package com.sams.demo.model.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class ReadTitleDTO extends BaseDTO {

    private String title;
    private String locale;
}