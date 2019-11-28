package com.sams.demo.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "TITLE")
@Table(name = "TITLE")
@Data
public class Title extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "LOCALE_ID", referencedColumnName = "LOCALE_ID")
    private Locale locale;

    @ManyToOne
    @JoinColumn(name = "QUESTION_ID", referencedColumnName = "QUESTION_ID")
    private Question question;

    @Column(name = "TITLE", nullable = false)
    private String title;

}
