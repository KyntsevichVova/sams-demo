package com.sams.demo.model.entity;

import lombok.Data;
import org.hibernate.annotations.ColumnTransformer;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "QUESTION")
@Data
public class Question extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "LINK", nullable = false)
    private String link;

    @Column(name = "LEVEL_ID", nullable = false)
    @Enumerated(EnumType.STRING)
    @ColumnTransformer(
            read  = "( SELECT L.LEVEL_NAME FROM `LEVEL` L WHERE L.ID = LEVEL_ID )",
            write = "( SELECT L.ID FROM `LEVEL` L WHERE L.LEVEL_NAME = ? )"
    )
    private Level level;

    public enum Level {
        JUNIOR,
        MIDDLE,
        SENIOR
    }
}