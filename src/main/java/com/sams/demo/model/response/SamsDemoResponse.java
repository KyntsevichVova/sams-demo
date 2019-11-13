package com.sams.demo.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.sams.demo.model.dto.BaseDTO;
import com.sams.demo.model.response.enums.ResponseStatus;
import lombok.Data;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
@JsonPropertyOrder( { "status", "data", "total" } )
@Data
public class SamsDemoResponse<DTO> {

    private Long total;
    private List<DTO> data;
    private ResponseStatus status;

    public SamsDemoResponse(ResponseStatus status) {
        this.status = status;
    }
}