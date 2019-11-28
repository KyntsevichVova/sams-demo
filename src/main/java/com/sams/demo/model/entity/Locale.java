package com.sams.demo.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity(name = "LOCALE")
@Table(name = "LOCALE")
@Data
public class Locale extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LOCALE_ID", nullable = false)
    private Long localeId;

    @Enumerated(EnumType.STRING)
    @Column(name = "LOCALE_CODE", nullable = false)
    private Code code;

    @OneToMany(mappedBy = "locale")
    private List<LevelLocalized> levelLocalizedList;

    @OneToMany(mappedBy = "locale")
    private List<Title> titleList;

    public enum Code {

        EN,
        RU
    }

}
