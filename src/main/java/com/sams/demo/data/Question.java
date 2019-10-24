package com.sams.demo.data;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "QUESTION")
@Data
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    @Size(max = 100)
    private String title;

    @NotNull
    @Size(max = 100)
    private String link;

    @NotNull
    @Size(max = 10)
    private String level;
}