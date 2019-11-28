package com.sams.demo.model.entity;

import lombok.Data;

import javax.persistence.*;

import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity(name = "QUESTION")
@Table(name = "QUESTION")
@Data
public class Question extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "QUESTION_ID", nullable = false)
    private Long id;

    @Column(name = "LINK", nullable = false)
    private String link;

    @ManyToOne
    @JoinColumn(name = "LEVEL_ID", referencedColumnName = "LEVEL_ID")
    private LevelCon level;

    @OneToMany(mappedBy = "question")
    private List<Title> titleList;

}