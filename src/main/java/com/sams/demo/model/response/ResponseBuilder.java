package com.sams.demo.model.response;

import com.sams.demo.model.dto.BaseDTO;
import com.sams.demo.model.entity.BaseEntity;
import com.sams.demo.model.error.ErrorMessage;
import com.sams.demo.model.mapper.IDTOMapper;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.sams.demo.model.response.enums.ResponseStatus.FAILURE;
import static com.sams.demo.model.response.enums.ResponseStatus.SUCCESS;

@Data
public class ResponseBuilder<DTO extends BaseDTO, ENTITY extends BaseEntity> {

    private SamsDemoResponse<DTO> response;
    private HttpStatus httpStatus;

    private ResponseBuilder() {
    }

    private ResponseBuilder(SamsDemoResponse<DTO> response) {
        this.response = response;
    }

    @SuppressWarnings("unchecked")
    public static  ResponseBuilder failure() {

        SamsDemoResponse response = new SamsDemoResponse<>(FAILURE);
        return new ResponseBuilder(response);
    }

    public static <DTO extends BaseDTO, ENTITY extends BaseEntity> ResponseBuilder<DTO, ENTITY> success() {

        SamsDemoResponse<DTO> response = new SamsDemoResponse<>(SUCCESS);
        return new ResponseBuilder<>(response);
    }

    public static ResponseBuilder empty() {

        return new ResponseBuilder();
    }

    public ResponseBuilder<DTO, ENTITY> withPageData(Page<ENTITY> page, IDTOMapper<DTO, ENTITY> mapper) {

        this.response.setData(mapper.mapToDTOList(page.getContent()));
        this.response.setTotal(page.getTotalElements());

        return this;
    }

    public ResponseBuilder<DTO, ENTITY> withData(List<ENTITY> list, IDTOMapper<DTO, ENTITY> mapper) {

        this.response.setData(mapper.mapToDTOList(list));
        this.response.setTotal((long) list.size());

        return this;
    }

    public ResponseBuilder withErrorMessage(List<ErrorMessage> errors) {

        this.response.setErrorData(errors);

        return this;
    }

    public ResponseBuilder<DTO, ENTITY> withHttpStatus(HttpStatus httpStatus) {

        this.httpStatus = httpStatus;

        return this;
    }

    public ResponseEntity<SamsDemoResponse<DTO>> build() {

        return new ResponseEntity<>(response, httpStatus);
    }
}