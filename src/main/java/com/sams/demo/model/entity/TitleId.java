package com.sams.demo.model.entity;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class TitleId implements Serializable {

    private Long localeId;

    private Long questionId;
}
