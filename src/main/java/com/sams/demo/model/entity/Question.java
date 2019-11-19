package com.sams.demo.model.entity;

import lombok.Data;
import org.hibernate.annotations.ColumnTransformer;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;

@Entity(name = "QUESTION")
@Table(name = "QUESTION")
@Data
public class Question extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "QUESTION_ID", nullable = false)
    private Long id;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "LINK", nullable = false)
    private String link;

    @Column(name = "level_id", nullable = false)
    @Enumerated(STRING)
    @ColumnTransformer(
            forColumn = "level_id",
            read  = "( SELECT LC.LEVEL_NAME FROM LEVEL_CON AS LC WHERE LC.LEVEL_ID = level_id )",
            write = "( SELECT LC.level_id FROM LEVEL_CON AS LC WHERE LC.LEVEL_NAME = ? )"
    )
    private Level level;

    public enum Level {

        JUNIOR,
        MIDDLE,
        SENIOR
    }
}