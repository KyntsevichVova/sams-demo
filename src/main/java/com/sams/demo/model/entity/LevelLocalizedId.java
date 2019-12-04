package com.sams.demo.model.entity;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class LevelLocalizedId implements Serializable {

    private Long levelId;

    private Long localeId;
}
