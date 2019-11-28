package com.sams.demo.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "LEVEL_LOCALIZED")
@Table(name = "LEVEL_LOCALIZED")
@Data
public class LevelLocalized extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "LEVEL_ID", referencedColumnName = "LEVEL_ID")
    private LevelCon levelCon;

    @ManyToOne
    @JoinColumn(name = "LOCALE_ID", referencedColumnName = "LOCALE_ID")
    private Locale locale;

    @Column(name = "LEVEL_LOCALIZED", nullable = false, length = 10)
    private String levelLocalized;

}
