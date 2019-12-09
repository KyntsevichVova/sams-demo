package com.sams.demo.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LocaleEnum {

    EN("en-US"),
    RU("ru-RU");

    private String value;
}