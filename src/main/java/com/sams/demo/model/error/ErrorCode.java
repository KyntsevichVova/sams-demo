package com.sams.demo.model.error;

public interface ErrorCode {

    String FIELD_MISSING = "field.missing";
    String FIELD_EMPTY = "field.empty";

    String ID_MISSING = "id.missing";
    String ACCESS_DATABASE_ERROR = "access.database.error";
    String ENTITY_NOT_FOUND = "entity.not.found";

    String LOCALE_NOT_SUPPORTED = "locale.not.supported";
    String PROCESS_LOCALE_ERROR = "process.locale.error";
}