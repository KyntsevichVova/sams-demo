package com.sams.demo.model.dto.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.sams.demo.model.enums.LocaleEnum;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static com.sams.demo.model.error.ErrorCode.LOCALE_INVALID;
import static com.sams.demo.model.error.exception.SamsDemoException.badRequestException;

@Slf4j
public class LocaleDeserializer extends JsonDeserializer<LocaleEnum> {

    @Override
    public LocaleEnum deserialize(
            JsonParser jsonParser,
            DeserializationContext deserializationContext) throws IOException, JsonProcessingException {

        LocaleEnum localeEnum;

        try {
            localeEnum = LocaleEnum.valueOf(jsonParser.getText().toUpperCase());
        } catch (Exception ex) {
            log.error("Bad request exception: locale is invalid, locale = {}", jsonParser.getText());
            throw badRequestException(LOCALE_INVALID, jsonParser.getText());
        }

        return localeEnum;
    }
}