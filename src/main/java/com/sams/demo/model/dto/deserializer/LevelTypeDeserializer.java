package com.sams.demo.model.dto.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.sams.demo.model.enums.LevelType;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static com.sams.demo.model.error.ErrorCode.LEVEL_TYPE_INVALID;
import static com.sams.demo.model.error.exception.SamsDemoException.badRequestException;

@Slf4j
public class LevelTypeDeserializer extends JsonDeserializer<LevelType> {

    @Override
    public LevelType deserialize(
            JsonParser jsonParser,
            DeserializationContext deserializationContext) throws IOException, JsonProcessingException {

        LevelType levelType;

        try {
            levelType = LevelType.valueOf(jsonParser.getText().toUpperCase());
        } catch (Exception ex) {
            log.error("Bad request exception: level type is invalid, level = {}", jsonParser.getText());
            throw badRequestException(LEVEL_TYPE_INVALID, jsonParser.getText());
        }

        return levelType;
    }
}