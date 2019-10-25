package com.sams.demo.data;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "QUESTION")
@Data
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private int id;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "LINK", nullable = false)
    private String link;

    @Column(name = "LEVEL", nullable = false, length = 10)
    private String level;
}