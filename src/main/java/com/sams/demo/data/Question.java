package com.sams.demo.data;

import javax.persistence.*;

@Entity
@Table(name = "QUESTION")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final int id;
    private final String title;
    private final String link;
    private final String level;

    private Question() {
        id = 0;
        title = null;
        link = null;
        level = null;
    }

    public Question(int id, String title, String link, String level) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.level = level;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getLevel() {
        return level;
    }
}