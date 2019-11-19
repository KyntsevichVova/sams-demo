package com.sams.demo.model.entity;

import lombok.Data;
import org.hibernate.annotations.ColumnTransformer;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity(name = "QUESTION")
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

    @Column(name = "DIFFICULTY_ID", nullable = false)
    @Enumerated(EnumType.STRING)
    @ColumnTransformer(
            read  = "( SELECT D.DIFFICULTY_NAME FROM DIFFICULTY D WHERE D.ID = DIFFICULTY_ID )",
            write = "( SELECT D.ID FROM DIFFICULTY D WHERE D.DIFFICULTY_NAME = ? )"
    )
    private Difficulty difficulty;

    public enum Difficulty {
        JUNIOR,
        MIDDLE,
        SENIOR
    }
}