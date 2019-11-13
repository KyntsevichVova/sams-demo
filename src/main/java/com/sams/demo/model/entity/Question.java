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

    @ColumnTransformer
    @Column(name = "LEVEL", nullable = false)
    private Level level;

    enum Level {

    }
}