package com.sams.demo.model.response;

import com.sams.demo.model.dto.BaseDTO;
import com.sams.demo.model.entity.BaseEntity;
import com.sams.demo.model.mapper.IDTOMapper;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.sams.demo.model.response.enums.ResponseStatus.SUCCESS;

@Data
public class ResponseBuilder<DTO extends BaseDTO, ENTITY extends BaseEntity> {

    private SamsDemoResponse<DTO> response;
    private HttpStatus httpStatus;

    public ResponseBuilder(SamsDemoResponse<DTO> response) {
        this.response = response;
    }

    public static <DTO extends BaseDTO, ENTITY extends BaseEntity> ResponseBuilder<DTO, ENTITY> success() {

        SamsDemoResponse<DTO> response = new SamsDemoResponse<>(SUCCESS);
        return new ResponseBuilder<>(response);
    }

    public ResponseBuilder<DTO, ENTITY> withPageData(Page<ENTITY> page, IDTOMapper<DTO, ENTITY> mapper) {

        this.response.setData(mapper.mapToDTOList(page.getContent()));
        this.response.setTotal(page.getTotalElements());

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